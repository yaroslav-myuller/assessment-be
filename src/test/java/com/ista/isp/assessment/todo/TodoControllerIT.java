package com.ista.isp.assessment.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ista.isp.assessment.todo.controllers.HomeController;
import com.ista.isp.assessment.todo.controllers.TodoController;
import com.ista.isp.assessment.todo.models.TodoEntity;
import com.ista.isp.assessment.todo.repositories.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class TodoControllerIT {

    @Value("${app.version}")
    private String appVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    private TodoController todoController;

    @Autowired
    private HomeController homeController;

    private final String url = "/todo";
    private final String todoEntityDescription = "Test todo item 1";
    private final TodoEntity todoEntity = new TodoEntity(todoEntityDescription);
    private final TodoEntity todoEntityInvalidSize = new TodoEntity("T");
    private final ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(todoEntity);
    String invalidSizeJson = objectMapper.writeValueAsString(todoEntityInvalidSize);

    TodoControllerIT() throws JsonProcessingException {
    }

    @AfterEach
    void cleanUp() {
        todoRepository.deleteAll();
    }

    @Test
    public void contextLoads() {
        assertThat(todoController).isNotNull();
        assertThat(homeController).isNotNull();
    }

    @Test
    public void shouldReturnStatusOkJsonTypeAndVersionWithConfigVersion() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("app-version", is(this.appVersion)));
    }

    @Test
    public void shouldReturnStatusOkJsonTypeOnGet() throws Exception {
        this.mockMvc.perform(get(url))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnStatusCreatedJsonTypeOnPost() throws Exception {
        this.mockMvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        )
//                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnSendDescriptionWithDoneFalseOnPost() throws Exception {
        this.mockMvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
//                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("description", is(this.todoEntityDescription)))
                .andExpect(jsonPath("done", is(false)));
    }

    @Test
    public void shouldReturnBadRequestOnPostInvalidJson() throws Exception {
        this.mockMvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidSizeJson)
        )
//                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnAlreadyReportedOnPostWithDuplicateDescription() throws Exception {
        todoRepository.saveAndFlush(todoEntity);
        this.mockMvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        )
//                .andDo(print())
                .andExpect(status().isAlreadyReported());
    }

    @Test
    public void shouldChangeDoneToTrueOnPut() throws Exception {
        todoRepository.saveAndFlush(todoEntity);
        final TodoEntity todoSavedEntity = todoRepository.findByDescription(todoEntityDescription);

        this.mockMvc.perform(put("/todo/" + todoSavedEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new TodoEntity(todoEntityDescription, true)))
        )
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("done", is(true)));
    }

}
