package com.taskmanager.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taskmanager.entity.Task;
import com.taskmanager.entity.TaskCategory;
import com.taskmanager.entity.TaskOwner;
import com.taskmanager.service.TaskOwnerService;
import com.taskmanager.service.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	private TaskService taskService;
	private TaskOwnerService taskOwnerService;
	
	//constructor injection
	public TaskController(TaskService theTaskService, TaskOwnerService theTaskOwners) {
		taskService = theTaskService;
		taskOwnerService = theTaskOwners;
	}
	
	// add mapping for "/list"

	@GetMapping("/list")
	public String listTasks(Model theModel) throws ParseException {
		
		//TEST Get the owner from DB
		//To be updated with:
		//Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = auth.getName();
		
		TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName); //Testing by adding id 1
		//TaskOwner theOwner = taskOwnerService.findByEmail(principal.); 
		Boolean displayCategories = !theOwner.getPrioritySelection().equals("Default");
		
		List<Task> theTasks = taskService.sortPriorityTasks(theOwner, theOwner.getTasks()); // Consider here adding the owner as param for algorithm to get preference information

		// add to the spring model - NEW consider including a second attribute for the owner
		theModel.addAttribute("tasks", theTasks);
		theModel.addAttribute("taskOwner", theOwner);
		theModel.addAttribute("displayCategories",displayCategories);
		
		return "tasks/list-tasks";
	}
	
	@GetMapping("/completedList")
	public String listCompleteTasks(Model theModel) throws ParseException {
		
		//TEST Get the owner from DB
		//To be updated with:
		//Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = auth.getName();
		TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName); //Testing by adding id 1
		
		List<Task> theTasks = taskService.findCompleted(theOwner.getTasks());

		// add to the spring model
		theModel.addAttribute("tasks", theTasks);
		
		return "tasks/list-complete-tasks";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		//create model attrigute to bind form data
		Task theTask = new Task();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = auth.getName();
		TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName);
		
		Boolean displayCategories = !theOwner.getPrioritySelection().equals("Default");
		Boolean isCreate = true;
		
		List<TaskCategory> selectionOptions = new ArrayList<TaskCategory>();
		
		for(TaskCategory tc : theOwner.getCategories()) {
			//selectionOptions.put(tc.getName(),tc );
			selectionOptions.add(tc);
		}
		
		
		//System.out.println("The Map value: " + selectionOptions);
		//System.out.println("The Map keyset value: " + selectionOptions);
		
		theModel.addAttribute("sortSelection",selectionOptions);
		theModel.addAttribute("task",theTask);
		theModel.addAttribute("displayCategories",displayCategories);
		theModel.addAttribute("taskOwner",theOwner);
		theModel.addAttribute("isCreate",isCreate);
		
		return "tasks/task-form";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("taskId") int theId, Model theModel) {
		//get the task from the service
		Task theTask = taskService.findById(theId);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = auth.getName();
		TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName);
		
		Boolean displayCategories = !theOwner.getPrioritySelection().equals("Default");
		Boolean isCreate = false;
		
		List<TaskCategory> selectionOptions = new ArrayList<TaskCategory>();
		
		for(TaskCategory tc : theOwner.getCategories()) {
			//selectionOptions.put(tc.getName(),tc );
			selectionOptions.add(tc);
		}
		
		
		//set task as a model attribute to pre-populate the form
		theModel.addAttribute("sortSelection",selectionOptions);
		theModel.addAttribute("task", theTask);
		theModel.addAttribute("displayCategories",displayCategories);
		theModel.addAttribute("taskOwner",theOwner);
		theModel.addAttribute("isCreate",isCreate);
		
		//send over to our form
		return "tasks/task-form";
	}
	
	@PostMapping("/save")
	public String saveTask(@ModelAttribute("task") Task theTask) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = auth.getName();
		TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName);
		
		if(theTask.getOwner() == null) {
			theTask.setOwner(theOwner);
		}
		
		if(theTask.getCategory() == null) {
			theTask.setCategory(theOwner.getCategories().get(0));
		}
		
		//Save the task
		taskService.save(theTask);
		
		//Use a redirect to prevent duplicate submission
		return "redirect:/tasks/list";
	}
	
	@GetMapping("/complete")
	public String complete(@RequestParam("taskId") int theId) {
		
		//get the task from the service
		Task theTask = taskService.findById(theId);
		//Save the task
		taskService.complete(theTask);
		//redirect to /employees/list
		return "redirect:/tasks/list";
		
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("taskId") int theId) {
		
		//delete the employee
		taskService.deleteById(theId);
				
		//redirect to /employees/list
		return "redirect:/tasks/list";
	}
}


