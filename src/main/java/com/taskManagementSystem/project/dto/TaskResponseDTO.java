package com.taskManagementSystem.project.dto;

import java.time.LocalDateTime;

import com.taskManagementSystem.project.entity.Task;
import com.taskManagementSystem.project.entity.TaskStatus;

import lombok.Data;

@Data
public class TaskResponseDTO {

	private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    
    
    public TaskResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}



	public TaskResponseDTO(Long id, String title, String description, LocalDateTime dueDate, TaskStatus status,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
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



	public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}



	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	

	@Override
	public String toString() {
		return "TaskResponseDTO [id=" + id + ", title=" + title + ", description=" + description + ", dueDate="
				+ dueDate + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}



	public static TaskResponseDTO fromEntity(Task task) {
    	if(task==null) {
    		return null;
    	}
    	TaskResponseDTO dto=new TaskResponseDTO();
    	dto.setId(task.getId());
    	dto.setTitle(task.getTitle());
    	dto.setDescription(task.getDescription());
    	dto.setDueDate(task.getDueDate());
    	dto.setStatus(task.getStatus());
    	dto.setCreatedAt(task.getCreatedAt());
    	dto.setUpdatedAt(task.getUpdatedAt());
    	return dto;
    	
    }
}
