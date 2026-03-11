package com.marko.todo_api.repository;

import com.marko.todo_api.model.Todo;
import com.marko.todo_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long> {

    public List<Todo> findByUser(User user);

}
