package com.taskmanager.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="task")
public class Task implements Comparable<Task> {

	// define fields
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	//In this method consider the use of username instead of the id for the relationship
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="owner_id")
	private TaskOwner owner;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="category_id")
	private TaskCategory category;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Transient
	private int combinedPriority;
	
	@Column(name="priority")
	private int priority;
	
	@Column(name="due_date")
	private String dueDate;
	
	@Column(name="created_date")
	private String createdDate;
	
	@Column(name="completed")
	private Boolean completed;
	
		
	// define constructors
	
	public Task() {
		
	}

	
	 public Task(TaskOwner owner, String title, String description, int priority, String dueDate,
			String createdDate, Boolean completed) {
		this.owner = owner;
		this.title = title;
		this.description = description;
		//this.combinedPriority = combinedPriority;
		this.priority = priority;
		this.dueDate = dueDate;
		this.createdDate = createdDate;
		this.completed = completed;}

	@Override
	    public int compareTo(Task task) {
		 return Integer.valueOf(this.combinedPriority).compareTo(Integer.valueOf(task.combinedPriority));
	     //return this.getPriority().compareTo(task.getPriority()); 
	    }
	 
	 //Getters and Setters
	 
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public TaskOwner getOwner() {
		return owner;
	}


	public void setOwner(TaskOwner owner) {
		this.owner = owner;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	

	public int getCombinedPriority() {
		return combinedPriority;
	}

	public void setCombinedPriority(int combinedPriority) {
		this.combinedPriority = combinedPriority;
	}

	public String getDueDate() {
		return dueDate;
	}


	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}


	public String getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}


	public Boolean getCompleted() {
		return completed;
	}


	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}


	public TaskCategory getCategory() {
		return category;
	}


	public void setCategory(TaskCategory category) {
		this.category = category;
	}


	@Override
	public String toString() {
		return "Task [id=" + id + ", owner=" + owner.getId() + ", category=" + category.getId()  + ", title=" + title + ", description="
				+ description + ", combinedPriority=" + combinedPriority + ", priority=" + priority + ", dueDate="
				+ dueDate + ", createdDate=" + createdDate + ", completed=" + completed + "]";
	}

	

	/*@Override
	public String toString() {
		return "Task [id=" + id + ", owner=" +  owner.getId() + ", title=" + title + ", description=" + description
				+ ", priority=" + priority + ", dueDate=" + dueDate + ", createdDate=" + createdDate + ", completed="
				+ completed + "]";
	}*/

	/*@Override
	public String toString() {
		return "Task [id=" + id + ", owner=" + owner.getId() + ", title=" + title + ", description=" + description
				+ ", combinedPriority=" + combinedPriority + ", priority=" + priority + ", dueDate=" + dueDate
				+ ", createdDate=" + createdDate + "]";
	}*/

	/*@Override
	public String toString() {
		return "Task [id=" + id + ", owner=" + owner.getId() + ", title=" + title + ", description=" + description
				+ ", priority=" + priority + ", dueDate=" + dueDate + ", createdDate=" + createdDate + "]";
	}
*/
	
	
	
	
		
}











