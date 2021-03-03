package com.ista.isp.assessment.todo.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Entity for saving TODOs with unique description with some validation and Numeric id as primary key
 */
@Entity
@Table(name = "todo")
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Integer must be enough for this list, otherwise Long

    @Column(unique = true, nullable = false) // to avoid saving duplicate TODOs
    @NotBlank(message = "Description is mandatory")
    @Size(min = 2, max = 255)
    private String description;

    @Column(nullable = false)
    private Boolean done = false;

    public TodoEntity() {
    }

    public TodoEntity(String description) {
        this();
        this.description = description;
    }

    public TodoEntity(String description, Boolean done) {
        this(description);
        this.done = done;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TodoEntity)) return false;
        TodoEntity that = (TodoEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(done, that.done);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, done);
    }

    @Override
    public String toString() {
        return "TodoEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", done=" + done +
                '}';
    }
}
