package com.taskmanager.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanager.entity.TaskOwner;

public interface TaskOwnerRepository extends JpaRepository<TaskOwner, Integer> {
	
	//add a method to sort by last name
	public List<TaskOwner> findAllByOrderByLastNameAsc();
	
	//
	public TaskOwner findByEmail(String email);

}
