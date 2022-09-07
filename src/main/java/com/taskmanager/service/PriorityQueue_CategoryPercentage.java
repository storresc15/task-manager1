package com.taskmanager.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import com.taskmanager.entity.Task;
import com.taskmanager.entity.TaskCategory;
import com.taskmanager.entity.TaskOwner;
import com.taskmanager.service.TaskServiceImpl.PriorityDefinitionStrategy;

public class PriorityQueue_CategoryPercentage implements PriorityDefinitionStrategy {
	
public List<Task> setPriority(TaskOwner theOwner, List<Task> theTasks) throws ParseException {
		
		List<Task> sortedTasks = new ArrayList<Task>();
		
		List<TaskCategory> categories = theOwner.getCategories();
		
		//Using streams and collectors work for building the map
		Map<String, Integer> categoryMap = categories.stream()
				.collect(Collectors.toMap(TaskCategory::getName,
						TaskCategory::getPriority,
						(a, b) -> { return (a+b)/2;})
						// Or (a, b) -> (a+b)/2
						);
		
		PriorityQueue<Task> priorityQueue = new PriorityQueue<>();
		for(Task theTask : theTasks) {
			if(theTask.getCompleted() == false) {
				priorityQueue.add(setCombinedPriority(theTask, categoryMap));
			}
		}
		
		while(true) {
			Task tempTask = priorityQueue.poll();
			if(tempTask == null) break;
			sortedTasks.add(tempTask);
		}

		
		Collections.reverse(sortedTasks);
		return sortedTasks;
	}
	
	private static Task setCombinedPriority(Task theTask, Map<String, Integer> categoryMap) throws ParseException {
		
		String category = theTask.getCategory().getName();
		//Test next time this one
		double categoryFactor = categoryMap.get(category) / 10;
		
		double k = -.9;
		Date d = new Date();
		Date cd = new SimpleDateFormat("yyyy-MM-dd").parse(theTask.getDueDate());		
		
		int CombinedPriority = (int) ( k * ((Math.abs(cd.getTime() - d.getTime()))/600000000) + categoryFactor/*p*/);
		
		theTask.setCombinedPriority(CombinedPriority);
		return theTask;
	}

}
