package com.arsh.twitter.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

@Entity
@Validated
public class User implements Serializable {
	@Id
	@GeneratedValue
	private int id;
	@NotEmpty(message = "Name should not be null or empty")
	private String name;
	@NotEmpty(message = "Handle should not be null or empty")
	private String handle;
	@NotNull
	private LocalDate dob;
	@NotEmpty(message = "Email should not be null or empty")
	private String email;
	@NotEmpty(message = "Contact should not be null or empty")
	private String contact;
	private ArrayList<Integer> followers;
	private ArrayList<Integer> followings;

	@OneToMany(mappedBy = "user")
	private Set<Tweet> tweets;

	public User(String name, String handle, LocalDate dob, String email, String contact) {
		super();
		this.followers = new ArrayList<>();
		this.followings = new ArrayList<>();
		this.tweets = new HashSet<>();
		this.name = name;
		this.handle = handle;
		this.dob = dob;
		this.email = email;
		this.contact = contact;
	}

	public User() {
		super();
		this.tweets = new HashSet<>();
		this.followers = new ArrayList<>();
		this.followings = new ArrayList<>();

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public ArrayList<Integer> getFollowers() {
		return followers;
	}

	public void setFollowers(ArrayList<Integer> followers) {
		this.followers = followers;
	}

	public ArrayList<Integer> getFollowings() {
		return followings;
	}

	public void setFollowings(ArrayList<Integer> followings) {
		this.followings = followings;
	}

	public Set getTweets() {
		return tweets;
	}

	public void setTweets(Set tweets) {
		this.tweets = tweets;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", handle=" + handle + ", dob=" + dob + ", email=" + email
				+ ", contact=" + contact + ", followers=" + followers + ", followings=" + followings + ", tweets="
				+ tweets + "]";
	}

}
