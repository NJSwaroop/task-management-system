package com.taskManagementSystem.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.taskManagementSystem.project.dto.TaskRequestDTO;
import com.taskManagementSystem.project.dto.TaskResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

public interface TaskApiDocs {
	
	@Operation(summary="Create a new Task",description="Adds a Task with a title and description. Status and DueDate default automatically if omitted.")
	ResponseEntity<TaskResponseDTO>createTask(
			@RequestBody(
					description="Task data payload. Provide title and description. Remaining by default values are arranged.",
					required = true,
					content = @Content(
							schema = @Schema(
									implementation = TaskRequestDTO.class,
									requiredProperties = {"title","description"}
									)
							)
					) 
					TaskRequestDTO taskRequestDTO);
	
	@Operation(summary="It will get all Tasks",description="Fetches a complete list of all Tasks available in Database.")
	ResponseEntity<List<TaskResponseDTO>>getAllTasks();
	
	@Operation(summary="Get a Task by ID",description="Retrives a single task's detailed records using it's database ID.")
	ResponseEntity<TaskResponseDTO>getTaskById(@Parameter(description="The unique DataBase ID of the Task to retireve",example="1")Long id);
	
	@Operation(summary="Update an exisiting Task", description="Modifies text fields or shifts status of a target task resource.")
	ResponseEntity<TaskResponseDTO>updateTask(@Parameter(description="The unique database ID of the task to update",example="1") Long id,TaskRequestDTO taskRequestDTO);
	
	@Operation(summary="Delete Task permanently",description="Deletes a task completely from the database.")
	ResponseEntity<String>deleteTask(@Parameter(description="The unique database ID of the task to delete permanently",example="1") Long id);
	
	@Operation(summary="Marks a Task as completed",description="It makes the status of a Task as COMPLETED directly.")
	ResponseEntity<TaskResponseDTO>completeTask(@Parameter(description = "The unique database ID of the task to mark as completed",example="1") Long id);
		

}
