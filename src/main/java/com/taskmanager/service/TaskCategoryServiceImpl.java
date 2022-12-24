package com.taskmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.dao.TaskCategoryRepository;
import com.taskmanager.entity.TaskCategory;

@Service
public class TaskCategoryServiceImpl implements TaskCategoryService {
	
	private TaskCategoryRepository taskCategoryRepository;
	@Autowired
	public TaskCategoryServiceImpl(TaskCategoryRepository theCategoryRepository) {
		taskCategoryRepository = theCategoryRepository;
	}

	@Override
	public List<TaskCategory> findAll() {
		return taskCategoryRepository.findAll();
	}

	@Override
	public void save(TaskCategory theTaskCategory) {
		taskCategoryRepository.save(theTaskCategory);
	}

	@Override
	public void deleteById(int theId) {

		taskCategoryRepository.deleteById(theId);
		
	}

	@Override
	public TaskCategory findById(int theId) {
		Optional<TaskCategory> result = taskCategoryRepository.findById(theId);
		
		TaskCategory theTaskCategory;
		if (result.isPresent()) {
			theTaskCategory = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find task id - " + theId);
		}
		
		return theTaskCategory;
	}

}
