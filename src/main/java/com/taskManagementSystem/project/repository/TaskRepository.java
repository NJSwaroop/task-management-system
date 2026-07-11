package com.taskManagementSystem.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskManagementSystem.project.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

}
