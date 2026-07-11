package com.taskManagementSystem.project.dto;

import java.time.LocalDateTime;

import com.taskManagementSystem.project.entity.Task;
import com.taskManagementSystem.project.entity.TaskStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequestDTO {
	
	
	@NotBlank(message="Title is required and cannot be blank")
	@Size(max=100, message ="Titel cannot exceed 100 characters")
	@Schema(description = "The title of the task", example = "Java Programming")
	private String title;
	
	@NotBlank(message="Description is required and cannot be blank")
	@Size(max=500,message="Description cannot exceed 500 characters")
	@Schema(description = "Detailed description of the task", example = "Gives detailed information about Java")
	private String description;
	
	@FutureOrPresent(message="Due date must be in present or future")
	//@Schema(hidden = true)
	private LocalDateTime dueDate;
	
	//@Schema(hidden = true)
	private TaskStatus status;
	
	
	
	
	
	public TaskRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}



	public TaskRequestDTO(String title, String description, LocalDateTime dueDate, TaskStatus status) {
		super();
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.status = status;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public LocalDateTime getDueDate() {
		return dueDate;
	}



	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}



	public TaskStatus getStatus() {
		return status;
	}



	public void setStatus(TaskStatus status) {
		this.status = status;
	}


	

	@Override
	public String toString() {
		return "TaskRequestDTO [title=" + title + ", description=" + description + ", dueDate=" + dueDate + ", status="
				+ status + "]";
	}


	
	//This is conversion Helper: Maps DTO to Entity	
	public Task toEntity() {
		Task task = new Task();
		task.setTitle(this.title);
		task.setDescription(this.description);
		task.setDueDate(this.dueDate);
		if(this.status!=null) {
			task.setStatus(this.status);
		}
		return task;
	}
	
}
