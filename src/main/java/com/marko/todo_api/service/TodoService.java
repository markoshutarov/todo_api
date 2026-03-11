package com.marko.todo_api.service;

import com.marko.todo_api.dto.TodoRequestDTO;
import com.marko.todo_api.dto.TodoResponseDTO;
import com.marko.todo_api.exception.TodoNotFoundException;
import com.marko.todo_api.model.Todo;
import com.marko.todo_api.model.User;
import com.marko.todo_api.repository.TodoRepository;
import com.marko.todo_api.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository){
        this.todoRepository=todoRepository;
        this.userRepository = userRepository;
    }

    public List<TodoResponseDTO> getAll(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found!"));
        return todoRepository.findByUser(user)
                .stream()
                .map(todo -> new TodoResponseDTO(todo.getId(),todo.getTitle(),todo.getDescription(),todo.isCompleted()))
                .collect(Collectors.toList());
    }

    public TodoResponseDTO getById(Long id){
        return todoRepository.findById(id)
                .map(todo -> new TodoResponseDTO(todo.getId(),todo.getTitle(),todo.getDescription(),todo.isCompleted()))
                .orElseThrow(()-> new TodoNotFoundException("Todo not found with id: " + id));
    }

    public TodoResponseDTO createTodo(TodoRequestDTO requestDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found!"));
        Todo todo = new Todo();
        todo.setTitle(requestDTO.getTitle());
        todo.setDescription(requestDTO.getDescription());
        todo.setCompleted(requestDTO.isCompleted());
        todo.setUser(user);
        Todo savedTodo=todoRepository.save(todo);
        return new TodoResponseDTO(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getDescription(),
                savedTodo.isCompleted()
        );
    }

    public TodoResponseDTO updateTodo(Long id, TodoRequestDTO requestDTO){
        Todo todo= todoRepository.findById(id).orElseThrow(()->new TodoNotFoundException("Todo not found with id: " + id));
        todo.setTitle(requestDTO.getTitle());
        todo.setDescription(requestDTO.getDescription());
        todo.setCompleted(requestDTO.isCompleted());
        Todo savedTodo=todoRepository.save(todo);
        return new TodoResponseDTO(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getDescription(),
                savedTodo.isCompleted()
        );
    }

    public void deleteTodoById(Long id){
        todoRepository.findById(id).orElseThrow(()->new TodoNotFoundException("Todo not found with id: " + id));
        todoRepository.deleteById(id);
    }
}
