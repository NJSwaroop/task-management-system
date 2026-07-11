package com.taskManagementSystem.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taskManagementSystem.project.dto.TaskRequestDTO;
import com.taskManagementSystem.project.dto.TaskResponseDTO;
import com.taskManagementSystem.project.entity.Task;
import com.taskManagementSystem.project.entity.TaskStatus;
import com.taskManagementSystem.project.exception.ResourceNotFoundException;
import com.taskManagementSystem.project.repository.TaskRepository;


@Service
public class TaskService {
	
	private final TaskRepository taskRepository;
	
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository=taskRepository;
	}
	
	
	//1.Retrieves all tasks from the database.
	public List<TaskResponseDTO> getAllTasks(){
		List<TaskResponseDTO>allData = taskRepository.findAll()
				.stream()
				.map(task -> TaskResponseDTO.fromEntity(task))
				.toList();
		return allData;
	}
	
	//2.Retrieves a specific task by ID.
	//If the ID doesn't exists, it will throw custom defined exception.
	public TaskResponseDTO getTaskById(Long id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not Foud with Id:"+id));
		return TaskResponseDTO.fromEntity(task);
	}
	
	
	//3.Create a new Task
	//It enforces that every new tasks starts with a status of 'PENDING'
	//unless explicitly specified otherwise
	public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
		Task task = taskRequestDTO.toEntity();
		if(task.getStatus()==null) {
				task.setStatus(TaskStatus.IN_PROGRESS);
		}
		
		java.time.LocalDateTime now = java.time.LocalDateTime.now();
		//task.setCreatedAt(now);
		//task.setUpdatedAt(now);
		
		if(task.getDueDate()==null) {
			task.setDueDate(now.plusDays(7));
		}
		
		Task savedTask = taskRepository.save(task);
		return TaskResponseDTO.fromEntity(savedTask);
	}
	
	
	//4.Update an existing task 
	//First fetch the existing task to ensure it exists.
	//Update only the allowed fields, then save it back.
	public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
		
		Task exisitingTask = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found with Id:"+id));
		
		exisitingTask.setTitle(taskRequestDTO.getTitle());
		exisitingTask.setDescription(taskRequestDTO.getDescription());
		exisitingTask.setDueDate(taskRequestDTO.getDueDate());
		
		//exisitingTask.setUpdatedAt(java.time.LocalDateTime.now());
		
		if(taskRequestDTO.getStatus()!=null) {
			exisitingTask.setStatus(taskRequestDTO.getStatus());
		}
		Task updatedTask = taskRepository.save(exisitingTask);
		return TaskResponseDTO.fromEntity(updatedTask);
	}
	
	
	//5. Delete a task.
	//Verify existence first so we can return a proper 404 error
	//if a client tries to delete a non-existent task.
	public void deleteTask(Long id) {
		Task exisitingTask = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found with Id:"+id));
		taskRepository.delete(exisitingTask);
	}
	
	
	//6.Patch operation, marks a task as complete.
	public TaskResponseDTO completeTask(Long id) {
		Task exisitingTask = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found with Id:"+id));
		
		exisitingTask.setStatus(TaskStatus.COMPLETED);
		Task completedTask = taskRepository.save(exisitingTask);
		
		return TaskResponseDTO.fromEntity(completedTask);
	}

}
