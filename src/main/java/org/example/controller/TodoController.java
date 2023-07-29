package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.TodoModel;
import org.example.model.TodoRequest;
import org.example.model.TodoResponse;
import org.example.service.TodoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/")
public class TodoController {
    private final TodoServiceImpl service;

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {
        log.info("CREATE");

        if (ObjectUtils.isEmpty(request.getTitle())) {
            return ResponseEntity.badRequest().build();
        }

        if (ObjectUtils.isEmpty(request.getOrder())) {
            request.setOrder(0L);
        }

        if (ObjectUtils.isEmpty(request.getCompleted())) {
            request.setCompleted(false);
        }

        TodoModel result = service.add(request);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> read(@PathVariable Long id) {
        log.info("READ ONE");
        TodoModel result = service.searchById(id);

        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        log.info("READ ALL");
        List<TodoModel> list = service.searchAll();

        List<TodoResponse> responses = list.stream().map(TodoResponse::new).toList();
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest request) {
        log.info("UPDATE");
        TodoModel result = service.updateById(id, request);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("DELETE");
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteAll() {
        log.info("DELETE ALL");
        service.deleteAll();
        return ResponseEntity.ok().build();
    }
}