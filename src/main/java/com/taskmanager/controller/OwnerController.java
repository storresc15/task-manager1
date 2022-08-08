package com.taskmanager.controller;

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
import com.taskmanager.entity.TaskOwner;
import com.taskmanager.entity.User;
import com.taskmanager.service.TaskOwnerService;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.UserService;

@Controller
@RequestMapping("/owner")
public class OwnerController {
	
private TaskOwnerService taskOwnerService;
private TaskService taskService;
private UserService userService;
	
	//constructor injection
	public OwnerController(TaskService theTaskService, TaskOwnerService theTaskOwners, UserService theUserService) {
		taskService = theTaskService;
		taskOwnerService = theTaskOwners;
		userService = theUserService;
	}
	
	// add mappings
	@GetMapping("/showOwnerPreferences")
	public String showOwnerPreferences(@RequestParam("ownerId") int theId, Model theModel) {
		//get the task from the service
		TaskOwner theTaskOwner = taskOwnerService.findById(theId);
		
		//Add the Sort Selection for user
		String[] selectionOptions = {
		          "Default", "CategoryPercent", "CategoryGrade"
		        };
		
		//Display the categories
		Boolean displayCategories = !theTaskOwner.getPrioritySelection().equals("Default");
		System.out.println("The display Categories selection: " + theTaskOwner.getPrioritySelection() + " Hence the boolean value: " + displayCategories);
		
		//set task as a model attribute to pre-populate the form
		theModel.addAttribute("taskOwner", theTaskOwner);
		theModel.addAttribute("sortSelection",selectionOptions);
		theModel.addAttribute("categories", theTaskOwner.getCategories());
		theModel.addAttribute("displayCategories", displayCategories);
		
		//send over to our form
		return "task-owners/owner-preferences";
	}
	
	@PostMapping("/save")
	public String savePreference(@ModelAttribute("taskOwner") TaskOwner theTaskOwner) {
		
		int userId = theTaskOwner.getUser().getId();
		
		User theUser = userService.findById(userId);
		
		theTaskOwner.setUser(theUser);
		
		//Save the task
		taskOwnerService.save(theTaskOwner);
		
		//Use a redirect to prevent duplicate submission
		return "redirect:updateSuccess?ownerId="+theTaskOwner.getId();
	}
	
	@GetMapping("/updateSuccess")
	public String showSuccessPage(@RequestParam("ownerId") int theId, Model theModel) {
		//send over to our form
		theModel.addAttribute("ownerId", theId);
		return "task-owners/update-success";
	}
	
}
