package com.taskmanager.service;

import java.util.List;

import com.taskmanager.entity.TaskCategory;

public interface TaskCategoryService {
	
	public List<TaskCategory> findAll();
	
	public TaskCategory findById(int theId);
	
	public void save(TaskCategory theTaskCategory);
	
	public void deleteById(int theId);

}
