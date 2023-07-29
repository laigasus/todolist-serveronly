package org.example.service;

import org.example.model.TodoModel;
import org.example.model.TodoRequest;

import java.util.List;


public interface TodoService {

    TodoModel add(TodoRequest request);

    TodoModel searchById(Long id);

    List<TodoModel> searchAll();

    TodoModel updateById(Long id, TodoRequest request);

    void deleteById(Long id);

    void deleteAll();
}