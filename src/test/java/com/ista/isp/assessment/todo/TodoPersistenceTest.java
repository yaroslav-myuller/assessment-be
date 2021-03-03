package com.ista.isp.assessment.todo;

import com.ista.isp.assessment.todo.models.TodoEntity;
import com.ista.isp.assessment.todo.repositories.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TodoPersistenceTest {

    @Autowired
    private TodoRepository todoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @AfterEach
    void cleanUp() {
        todoRepository.deleteAll();
        entityManager.clear();
    }

    @Test
    public void testCreateAndGetTodoEntity() {
        final String description = "Test TODO";
        TodoEntity todoEntity = new TodoEntity(description);
        todoRepository.saveAndFlush(todoEntity);

        TodoEntity getTodoEntity = todoRepository.findByDescription(description);
        assertEquals(todoEntity, getTodoEntity);
    }

    @Test
    public void testCreateAndDeleteTodoEntity() {
        TodoEntity todoEntity = new TodoEntity("Test TODO 1");
        todoRepository.saveAndFlush(todoEntity);
        todoRepository.deleteAll();
        List<TodoEntity> all = todoRepository.findAll();
        assertEquals(all.size(), 0);
    }

    @Test
    public void testCreateAndUpdateTodoEntity() {
        final String description = "Test TODO 2";
        List<TodoEntity> todoEntityList = Arrays.asList(
                new TodoEntity(description),
                new TodoEntity("Test TODO 3")
        );
        todoRepository.saveAll(todoEntityList);
        todoRepository.flush();
        TodoEntity todoCreatedEntity = todoRepository.findByDescription(description);
        assertEquals(todoCreatedEntity.getDone(), false);
        todoCreatedEntity.setDone(true);
        todoRepository.save(todoCreatedEntity);
        TodoEntity todoUpdateEntity = todoRepository.findByDescription(description);
        assertEquals(todoUpdateEntity.getDone(), true);
    }

}
