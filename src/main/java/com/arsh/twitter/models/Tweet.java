package com.arsh.twitter.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.arsh.twitter.utilities.ChronologyUtility;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Validated
public class Tweet implements Serializable{

	@Id
	@GeneratedValue
	private int id;
	@NotEmpty(message = "Title should not be null or empty")
	private String title;
	@NotEmpty(message = "Category should not be null or empty")
	private String category;
	@NotEmpty(message = "Description should not be null or empty")
	private String Description;
	private String time;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;

	public Tweet() {
		super();
		this.time = ChronologyUtility.getCurrentFormattedTime();
	}

	public Tweet(String title, String category, String description) {
		super();
		this.title = title;
		this.category = category;
		Description = description;
		
		this.time = ChronologyUtility.getCurrentFormattedTime();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tweet(String title, String category, String description, String time) {
		super();
		this.title = title;
		this.category = category;
		Description = description;
		this.time = time;
	}

}
