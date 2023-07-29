package org.example.service;

import org.example.model.TodoModel;
import org.example.model.TodoRequest;
import org.example.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    @Test
    void add() {
        Mockito.when(todoRepository.save(ArgumentMatchers.any(TodoModel.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        TodoRequest expected = new TodoRequest();
        expected.setTitle("Test Title");

        TodoModel actual = todoService.add(expected);

        assertEquals(expected.getTitle(), actual.getTitle());
    }

    @Test
    void searchById() {
        TodoModel entity = new TodoModel();
        entity.setId(123L);
        entity.setTitle("title");
        entity.setOrder(0L);
        entity.setCompleted(false);
        Optional<TodoModel> optional = Optional.of(entity);

        given(todoRepository.findById(anyLong())).willReturn(optional);

        TodoModel actual = todoService.searchById(123L);

        TodoModel expected = optional.get();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getOrder(), actual.getOrder());
        assertEquals(expected.getCompleted(), actual.getCompleted());
    }

    @Test
    void searchByIdFailed() {
        given(todoRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> todoService.searchById(123L));
    }
}
