package org.example.data;
import org.example.model.Person;
import org.example.model.Todo;
import java.util.Collection;

public class TodoItems {
    Todo create(Todo todo) {
        return null;
    }

    Collection<Todo> findAll() {
        return null;
    }

    Todo findById(int id) {
        return null;
    }

    Collection<Todo> findByDoneStatus(boolean done) {
        return null;
    }

    Collection<Todo> findByAssignee(int assigneeId) {
        return null;
    }

    Collection<Todo> findByAssignee(Person assignee) {
        return null;
    }

    Collection<Todo> findByUnassignedTodoItems() {
        return null;
    }

    Todo update(Todo todoToUpdate) {
        return null;
    }

    boolean deleteById(int id) {
        return false;
    }
}
