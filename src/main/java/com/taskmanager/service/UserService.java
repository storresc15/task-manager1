package com.taskmanager.service;

import com.taskmanager.entity.User;

public interface UserService {
	
	public User findById(int theId);
	
	public void save(User theUser);

}
