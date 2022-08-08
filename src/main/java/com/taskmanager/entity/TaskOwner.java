package com.taskmanager.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name="owner")
public class TaskOwner {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	//Consider here adding the priority selection for task owner
	@Column(name="priority_selection")
	private String prioritySelection;
	
	@OneToMany(mappedBy="owner",
			cascade= {CascadeType.PERSIST, CascadeType.MERGE,
					CascadeType.DETACH, CascadeType.REFRESH})
	private List<Task> tasks;
	
	@OneToMany(mappedBy="owner",
			cascade= {CascadeType.PERSIST, CascadeType.MERGE,
					CascadeType.DETACH, CascadeType.REFRESH})
	private List<TaskCategory> categories;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	public TaskOwner() {
		
	}
		

	public TaskOwner(int id, String firstName, String lastName, String email, String prioritySelection,
			List<Task> tasks, List<TaskCategory> categories, User user) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.prioritySelection = prioritySelection;
		this.tasks = tasks;
		this.categories = categories;
		this.user = user;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	

	public List<TaskCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<TaskCategory> categories) {
		this.categories = categories;
	}

	public String getPrioritySelection() {
		return prioritySelection;
	}

	public void setPrioritySelection(String prioritySelection) {
		this.prioritySelection = prioritySelection;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "TaskOwner [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", prioritySelection=" + prioritySelection + ", tasks=" + tasks + ", categories=" + categories
				+ ", user=" + user.getId() + "]";
	}

}
