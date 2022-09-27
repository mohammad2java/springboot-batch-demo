package com.amir.batch.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amir.batch.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
