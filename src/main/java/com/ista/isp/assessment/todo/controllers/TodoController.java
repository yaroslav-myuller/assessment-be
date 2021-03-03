package com.ista.isp.assessment.todo.controllers;

import com.ista.isp.assessment.todo.models.TodoEntity;
import com.ista.isp.assessment.todo.repositories.TodoRepository;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

/**
 * The REST controller for handling of TodoEntity with GET, POST, PUT and DELETE
 */
@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<TodoEntity> list() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoEntity create(@Valid @RequestBody final TodoEntity todoEntity) {
        TodoEntity todoFound = repository.findByDescription(todoEntity.getDescription());
        if (todoFound == null) {
            return repository.save(todoEntity);
        }
        throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED);
    }

    @PutMapping("{id}")
    public TodoEntity update(@Valid @RequestBody TodoEntity todoUpdate, @PathVariable Integer id) throws NotFoundException {
        return repository.findById(id)
                .map(todo -> {
                    todo.setDescription(todoUpdate.getDescription());
                    todo.setDone(todoUpdate.getDone());
                    return repository.saveAndFlush(todo);
                })
                .orElseThrow(() -> new NotFoundException("Todo " + id + " is not found!"));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable final Integer id) throws NotFoundException {
        try {
            repository.deleteById(id);
        } catch (final Exception e) {
            throw new NotFoundException("Todo " + id + " is not found!", e);
        }
    }
}
