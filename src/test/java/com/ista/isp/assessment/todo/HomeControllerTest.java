package com.ista.isp.assessment.todo;

import com.ista.isp.assessment.todo.controllers.HomeController;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class HomeControllerTest {
    @Value("${app.version}")
    private String appVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HomeController homeController;

    @Test
    public void contextLoads() {
        assertThat(homeController).isNotNull();
    }

    @Test
    public void shouldReturnStatusOkJsonTypeAndVersionWithConfigVersion() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("app-version", is(this.appVersion)));
    }


}
