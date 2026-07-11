package com.taskManagementSystem.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskManagementSystem.project.dto.TaskRequestDTO;
import com.taskManagementSystem.project.dto.TaskResponseDTO;
import com.taskManagementSystem.project.service.TaskService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/tasks")
public class TaskController implements TaskApiDocs{
	
	private final TaskService taskServivce;
	
	public TaskController(TaskService taskService) {
		this.taskServivce = taskService;
	}
	
	
	//1.creates a new task
	//POST http://localhost:8080/api/tasks
	@PostMapping
	public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO){
		
		TaskResponseDTO createdTask = taskServivce.createTask(taskRequestDTO);
		return new ResponseEntity<>(createdTask,HttpStatus.CREATED);	
	}
	
	
	//2. GET ALL tasks
    //GET http://localhost:8080/api/tasks
	@GetMapping
	public ResponseEntity<List<TaskResponseDTO>> getAllTasks(){
		
		List<TaskResponseDTO> tasks = taskServivce.getAllTasks();
		return new ResponseEntity<>(tasks,HttpStatus.OK);
	}
	
	
	//3. GET a specific task BY ID
    //GET http://localhost:8080/api/tasks/{id}
	@GetMapping("/{id}")
	public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id){
		TaskResponseDTO task = taskServivce.getTaskById(id);
		return new ResponseEntity<>(task,HttpStatus.OK);
	}
	
	
	//4. UPDATE an entire task by ID
    //PUT http://localhost:8080/api/tasks/{id}
	@PutMapping("/{id}")
	public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody TaskRequestDTO taskRequestDTO){
		TaskResponseDTO updateTask = taskServivce.updateTask(id, taskRequestDTO); 
		return new ResponseEntity<>(updateTask,HttpStatus.OK);
	}
	
	
	//5. DELETE a task by ID
    //DELETE http://localhost:8080/api/tasks/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable Long id){
		taskServivce.deleteTask(id);
		return new ResponseEntity<>("Task deleted Successfully with id: "+id,HttpStatus.OK);
	}
	
	
	@PatchMapping("/{id}/complete")
	public ResponseEntity<TaskResponseDTO> completeTask(@PathVariable Long id){
		TaskResponseDTO completedTask = taskServivce.completeTask(id);
		return new ResponseEntity<>(completedTask,HttpStatus.OK);
	}
	
	

}
