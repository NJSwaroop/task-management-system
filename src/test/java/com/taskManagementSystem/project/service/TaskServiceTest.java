package com.taskManagementSystem.project.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.taskManagementSystem.project.dto.TaskRequestDTO;
import com.taskManagementSystem.project.dto.TaskResponseDTO;
import com.taskManagementSystem.project.entity.Task;
import com.taskManagementSystem.project.entity.TaskStatus;
import com.taskManagementSystem.project.exception.ResourceNotFoundException;
import com.taskManagementSystem.project.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

	@Mock
	private TaskRepository taskRepository;
	
	@InjectMocks
	private TaskService taskService;
	
	private Task sampleTask;
	private TaskRequestDTO taskRequestDTO;
	
	
	@BeforeEach
	void setUp() {
		//Initializes fresh sample data before every single test case run
		sampleTask = new Task();
		sampleTask.setId(1L);
		sampleTask.setTitle("Original Title");
		sampleTask.setDescription("Original Description");
		sampleTask.setStatus(TaskStatus.PENDING);
		sampleTask.setDueDate(LocalDateTime.now().plusDays(5));
		sampleTask.setCreatedAt(LocalDateTime.now().minusDays(1));
		sampleTask.setUpdatedAt(LocalDateTime.now().minusDays(1));
		
		taskRequestDTO = new TaskRequestDTO();
		taskRequestDTO.setTitle("Test Task");
		taskRequestDTO.setDescription("Test Description");
	}
	
	@Test
	void updateTask_ShouldReturnUpdatedTaskDTO_WhenTaskExists() {
		
		//Arrange
		LocalDateTime historicalPast = LocalDateTime.now().minusDays(2);
		sampleTask.setCreatedAt(historicalPast);
		sampleTask.setUpdatedAt(historicalPast);
		
		LocalDateTime newDueDate = LocalDateTime.now().plusDays(2);
		
		TaskRequestDTO updateRequest = new TaskRequestDTO();
		updateRequest.setTitle("Brand New Title");
		updateRequest.setDescription("Updated Description");
		updateRequest.setDueDate(newDueDate);
		updateRequest.setStatus(TaskStatus.IN_PROGRESS);
		
		when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
		
		when(taskRepository.save(any(Task.class))).thenAnswer(invocation->invocation.getArgument(0));
		
		//Act
		TaskResponseDTO result = taskService.updateTask(1L,updateRequest);
		
		//Assert
		assertThat(result).isNotNull();
		assertThat(result.getTitle()).isEqualTo("Brand New Title");
		assertThat(result.getDescription()).isEqualTo("Updated Description");
		assertThat(result.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
		assertThat(result.getDueDate()).isEqualTo(newDueDate);
		
		assertThat(result.getUpdatedAt()).isAfterOrEqualTo(historicalPast);
		
		
		verify(taskRepository,times(1)).findById(1L);
		verify(taskRepository,times(1)).save(any(Task.class));
		
	}
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																															
	@Test
	void updateTask_ShouldThrowResourceNotFoundException_WhenTaskDoesNotExist() {
		
		//Arrange
		Long nonExisitentId=999L;
		TaskRequestDTO updateRequest = new TaskRequestDTO();
		updateRequest.setTitle("New Title");
		
		when(taskRepository.findById(nonExisitentId)).thenReturn(Optional.empty());
		
		//Act&Assert
		assertThrows(ResourceNotFoundException.class,() -> {
			taskService.updateTask(nonExisitentId, updateRequest);
		});
		
		verify(taskRepository,times(1)).findById(nonExisitentId);
		verify(taskRepository,never()).save(any(Task.class));
		
	}
	
	
}
