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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		Boolean isCreate = true;
		
		
		theModel.addAttribute("taskCategory",theTaskCategory);
		theModel.addAttribute("taskOwner", theOwner);
		theModel.addAttribute("isCreate", isCreate);
		
		
		return "categories/category-form";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("categoryId") int theId, Model theModel) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = auth.getName();
		
		//get the task from the service
		TaskCategory theTaskCategory = categoryService.findById(theId);
		TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName);
		Boolean isCreate = false;
		
		//set task as a model attribute to pre-populate the form
		theModel.addAttribute("taskCategory", theTaskCategory);
		theModel.addAttribute("taskOwner", theOwner);
		theModel.addAttribute("isCreate", isCreate);
		
		//send over to our form
		return "categories/category-form";
	}
	
	//Save Action
	
	@PostMapping("/save")
	public String saveTaskCategory(@ModelAttribute("taskCategory") TaskCategory theTaskCategory, RedirectAttributes redirectAttrs) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = auth.getName();
		TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName);
		
		if(theTaskCategory.getOwner() == null) {
			theTaskCategory.setOwner(theOwner);
		}
		if(theTaskCategory.getPriority() == 0) {
			theTaskCategory.setPriority(1);
		}
		if(theTaskCategory.getPercentage() == 0) {
			theTaskCategory.setPercentage(1);
		}
		
		int percent = 0;
		
		for(TaskCategory tc : theOwner.getCategories()) {
			if(tc.getId() != theTaskCategory.getId()) {
			percent += tc.getPercentage();
			}
		}
		percent += theTaskCategory.getPercentage();
		//Error handling on Category Save if % goes over 100%
		if(percent > 100){
			//model.addAttribute("errorMessage","Percentage cannot go over 100 %");
			redirectAttrs.addFlashAttribute("errorMessage","Percentage cannot go over 100 %");
		    return "redirect:/categories/showFormForUpdate?categoryId="+theTaskCategory.getId();
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
