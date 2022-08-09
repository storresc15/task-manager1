package com.taskmanager.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.taskmanager.entity.TaskCategory;
import com.taskmanager.entity.TaskOwner;
import com.taskmanager.entity.User;
import com.taskmanager.service.TaskCategoryService;
import com.taskmanager.service.TaskOwnerService;
import com.taskmanager.service.UserService;

@Controller
public class LoginController {
	
	private UserService userService;
	private TaskOwnerService taskOwnerService;
	private TaskCategoryService taskCategoryService;
	
	public LoginController(UserService theUserService, TaskOwnerService theTaskOwnerService, TaskCategoryService theCategoryService) {
		userService = theUserService;
		taskOwnerService = theTaskOwnerService;
		taskCategoryService = theCategoryService;
	}

	@GetMapping("/login")
	public String showMyLoginPage() {
		
		// return "plain-login";

		return "login";
		
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
	    model.addAttribute("user", new User());
	    model.addAttribute("owner", new TaskOwner());
	     
	    return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user, TaskOwner owner) {
		
		System.out.println("------------------------Inside the process Register the user: " + user);
		
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
	    user.setPassword(encodedPassword);
	    
	    //Add the required values to owner and user
	    owner.setEmail(user.getUsername());
	    owner.setPrioritySelection("Default");
	    owner.setUser(user);
	    user.setEnabled(true);
	    user.setRole("ROLE_USER");
	    
	    
	    System.out.println("Inside the process Register the user: " + user);
	    System.out.println("Inside the process Register the owner: " + owner);
	     
	    userService.save(user);
	    taskOwnerService.save(owner);
	    
	  //Assigning Default Category for user
	    TaskCategory category = new TaskCategory();
	    category.setName("Default");
	    category.setPercentage(1);
	    category.setPercentage(1);
	    category.setOwner(owner);
	    
	    taskCategoryService.save(category);
	     
	    return "register_success";
	}
}
