package com.arsh.twitter.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arsh.twitter.exceptions.InvalidDataException;
import com.arsh.twitter.exceptions.UserNotFoundException;
import com.arsh.twitter.models.User;
import com.arsh.twitter.services.FollowService;
import com.arsh.twitter.services.TweetService;
import com.arsh.twitter.services.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	TweetService tweetService;
	@Autowired
	FollowService followService;

	@RequestMapping(value = "/")
	public String greet() {
		return "<h1>Hello User Welcome to the Twitter app</h1>";
	}

	@GetMapping("/users")
	public List<User> findAllUser() throws UserNotFoundException {
		return userService.findAll();
	}

	@GetMapping("/users/{id}")
	public User findUserById(@PathVariable int id) throws UserNotFoundException {
		return userService.findById(id);
	}

	@GetMapping("/userExceptionsLog")
	public String getuserExceptionLog() throws IOException {
		return userService.getUserExceptionsLog();
	}

	@GetMapping("/operationsLog")
	public String getOperationsLog() {
		return userService.getOperationsLog();
	}

	@GetMapping("/tweetExceptionsLog")
	public String getTweetExceptionsLog() throws IOException {
		return tweetService.getTweetExceptionsLog();
	}

	@GetMapping("/followExceptionsLog")
	public String getFollowExceptionsLog() throws IOException {
		return followService.getFollowExceptionsLog();
	}

	@PostMapping("/users")
	public User saveUser(@Valid @RequestBody User user, Errors error) throws InvalidDataException {
		if (error.hasErrors()) {
			String message = "";
			List<FieldError> list = error.getFieldErrors();
			for (FieldError f : list)
				message += f.getDefaultMessage() + ", ";
			throw new InvalidDataException(message);
		}

		User temp = userService.save(user);
		return temp;
	}

	@PutMapping("/users")
	public User updateUser(@Valid @RequestBody User user, Errors error)
			throws UserNotFoundException, InvalidDataException {
		if (error.hasErrors()) {
			String message = "";
			List<FieldError> list = error.getFieldErrors();
			for (FieldError f : list)
				message += f.getDefaultMessage() + ", ";
			throw new InvalidDataException(message);
		}
		User temp = userService.update(user);
		return temp;
	}

	@DeleteMapping("/users")
	public void deleteAllUsers() throws UserNotFoundException {
		userService.deleteAll();
		;
	}

	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable int id) throws UserNotFoundException {
		userService.deleteById(id);
	}

}
