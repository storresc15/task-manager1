package com.taskmanager.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanager.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {


	// that's it ... no need to write any code LOL!
	
	//add a method to sort by last name
	//public List<Task> findAllByOwner();
	
	
}
