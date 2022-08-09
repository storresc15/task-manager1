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

import com.taskmanager.entity.TaskCategory;
import com.taskmanager.entity.TaskOwner;
import com.taskmanager.service.TaskCategoryService;
import com.taskmanager.service.TaskOwnerService;

@Controller
@RequestMapping("/categories")
public class CategoryController {
	
	private TaskCategoryService categoryService;
	private TaskOwnerService taskOwnerService;
	
	public CategoryController(TaskCategoryService theCategoryService, TaskOwnerService theTaskOwnerService) {
		categoryService = theCategoryService;
		taskOwnerService = theTaskOwnerService;
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		//create model attrigute to bind form data
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = auth.getName();
		
		TaskCategory theTaskCategory = new TaskCategory();
		TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName);
		
		theModel.addAttribute("taskCategory",theTaskCategory);
		theModel.addAttribute("taskOwner", theOwner);
		
		return "categories/category-form";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("categoryId") int theId, Model theModel) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = auth.getName();
		
		//get the task from the service
		TaskCategory theTaskCategory = categoryService.findById(theId);
		TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName);
		
		//set task as a model attribute to pre-populate the form
		theModel.addAttribute("taskCategory", theTaskCategory);
		theModel.addAttribute("taskOwner", theOwner);
		
		//send over to our form
		return "categories/category-form";
	}
	
	//Save Action
	
	@PostMapping("/save")
	public String saveTaskCategory(@ModelAttribute("taskCategory") TaskCategory theTaskCategory) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = auth.getName();
		if(theTaskCategory.getOwner() == null) {
			TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName);
			theTaskCategory.setOwner(theOwner);
		}
		if(theTaskCategory.getPriority() == 0) {
			theTaskCategory.setPriority(1);
		}
		if(theTaskCategory.getPercentage() == 0) {
			theTaskCategory.setPercentage(1);
		}
		
		//Save the task
		categoryService.save(theTaskCategory);
		
		//Use a redirect to prevent duplicate submission
		return "redirect:/owner/showOwnerPreferences?ownerId="+theTaskCategory.getOwner().getId();
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("categoryId") int theId) {
		
		TaskCategory theTaskCategory = categoryService.findById(theId);
		
		//delete the Category
		categoryService.deleteById(theId);
		
				
		//redirect to /employees/list
		return "redirect:/owner/showOwnerPreferences?ownerId="+theTaskCategory.getOwner().getId();
	}
	

}
