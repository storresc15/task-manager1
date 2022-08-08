package com.taskmanager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanager.dao.UserRepository;
import com.taskmanager.entity.User;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository theUserRepository) {
		userRepository = theUserRepository;
	}
	
	@Override
	public User findById(int theId) {
		Optional<User> result = userRepository.findById(theId);
		
		User theUser;
		if (result.isPresent()) {
			theUser = result.get();
		}
		else {
			// we didn't find the user
			throw new RuntimeException("Did not find task id - " + theId);
		}
		
		return theUser;
	}

	@Override
	public void save(User theUser) {
		userRepository.save(theUser);
		
	}

}
