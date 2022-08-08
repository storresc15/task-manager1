package com.taskmanager.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.dao.TaskRepository;
import com.taskmanager.entity.Task;
import com.taskmanager.entity.TaskOwner;

@Service
public class TaskServiceImpl implements TaskService {

	private TaskRepository taskRepository;
	
	@Autowired
	public TaskServiceImpl(TaskRepository theTaskRepository) {
		taskRepository = theTaskRepository;
	}
	
	@Override
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	@Override
	public Task findById(int theId) {
		Optional<Task> result = taskRepository.findById(theId);
		
		Task theTask;
		if (result.isPresent()) {
			theTask = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find task id - " + theId);
		}
		
		return theTask;
	}

	@Override
	public void save(Task theTask) {
		if(theTask.getCreatedDate() == "" || theTask.getCreatedDate() == null) {
			String createdDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			theTask.setCreatedDate(createdDate);
		}
		taskRepository.save(theTask);
	}
	
	@Override
	public void complete(Task theTask) {
		theTask.setCompleted(true);
		taskRepository.save(theTask);
	}

	@Override
	public void deleteById(int theId) {
		taskRepository.deleteById(theId);
	}

	@Override
	public List<Task> sortPriorityTasks(TaskOwner theOwner, List<Task> tasks) {
		//Use the sortAlgorithm method from task service to return sorted by priority tasks
		//NEW: Add here the method to get the user preferences to select at runtime the algorithm to get the priority
		List<Task> sortedTasks = null;
		try {
			//To be updated once the strategy is in place
			PriorityDefinitionStrategy pq = PriorityDefinitionStrategyFactory.getPriorityDefinitionStrategy(theOwner.getPrioritySelection());
			//PriorityQueue_Utility pq = new PriorityQueue_Utility();
			sortedTasks = pq.setPriority(theOwner, tasks);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sortedTasks;
	}

	@SuppressWarnings("null")
	@Override
	public List<Task> findCompleted(List<Task> tasks) {
		List<Task> completedTasks = null;
		for(Task theTask : tasks) {
			if(theTask.getCompleted() == true) {
				completedTasks.add(theTask);
			}
		}
		return completedTasks;
	}
	
	
	//This interface is to be implemented by all the sorting options for priority
	//Use of the Strategy Factory to set the correct strategy based on options
	public interface PriorityDefinitionStrategy {
		List<Task> setPriority(TaskOwner theOwner, List<Task> tasks) throws ParseException;
	}

}






