package com.ista.isp.assessment.todo.repositories;

import com.ista.isp.assessment.todo.models.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {
    TodoEntity findByDescription(String description);
}
