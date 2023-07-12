package com.vitu.todoListchallenge.repository;

import com.vitu.todoListchallenge.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}

