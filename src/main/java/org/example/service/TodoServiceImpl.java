package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.TodoModel;
import org.example.model.TodoRequest;
import org.example.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public TodoModel add(TodoRequest request) {
        TodoModel todoModel = TodoModel.builder()
                .title(request.getTitle())
                .order(request.getOrder())
                .completed(request.getCompleted())
                .build();

        return todoRepository.save(todoModel);
    }

    @Override
    public TodoModel searchById(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<TodoModel> searchAll() {
        return todoRepository.findAll();
    }

    @Override
    public TodoModel updateById(Long id, TodoRequest request) {
        TodoModel todoModel = searchById(id);
        if (request.getTitle() != null) {
            todoModel.setTitle(request.getTitle());
        }

        if (request.getOrder() != null) {
            todoModel.setOrder(request.getOrder());
        }


        if (request.getCompleted() != null) {
            todoModel.setCompleted(request.getCompleted());
        }

        return todoRepository.save(todoModel);
    }

    @Override
    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        todoRepository.deleteAll();
    }
}