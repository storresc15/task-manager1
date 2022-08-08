package com.taskmanager.service;

import java.util.List;

import com.taskmanager.entity.TaskOwner;

public interface TaskOwnerService {

	public List<TaskOwner> findAll();
	
	public TaskOwner findById(int theId);
	
	public void save(TaskOwner theTask);
	
	public void deleteById(int theId);

	public TaskOwner findByEmail(String email);
	
}
