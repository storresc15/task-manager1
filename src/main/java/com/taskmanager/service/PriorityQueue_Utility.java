package com.taskmanager.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

import com.taskmanager.entity.Task;
import com.taskmanager.entity.TaskOwner;
import com.taskmanager.service.TaskServiceImpl.PriorityDefinitionStrategy;

public class PriorityQueue_Utility implements PriorityDefinitionStrategy {
	
	public List<Task> setPriority(TaskOwner theOwner, List<Task> theTasks) throws ParseException {
		
		List<Task> sortedTasks = new ArrayList<Task>();
		PriorityQueue<Task> priorityQueue = new PriorityQueue<>();
		for(Task theTask : theTasks) {
			if(theTask.getCompleted() == false) {
				priorityQueue.add(setCombinedPriority(theTask));
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
	
	private static Task setCombinedPriority(Task theTask) throws ParseException {
		
		double k = -.9;
		Date d = new Date();
		Date cd = new SimpleDateFormat("yyyy-MM-dd").parse(theTask.getDueDate());		
		
		int CombinedPriority = (int) ( k * ((Math.abs(cd.getTime() - d.getTime()))/600000000) + theTask.getPriority()/*p*/);
		
		theTask.setCombinedPriority(CombinedPriority);
		return theTask;
	}

}
