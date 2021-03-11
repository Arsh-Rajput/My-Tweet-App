package com.arsh.twitter.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arsh.twitter.exceptions.InvalidDataException;
import com.arsh.twitter.exceptions.InvalidFollowException;
import com.arsh.twitter.exceptions.UserNotFoundException;
import com.arsh.twitter.models.User;
import com.arsh.twitter.services.FollowService;

@RestController
@RequestMapping("/users/{userId}")
public class FollowController {

	@Autowired
	FollowService followService;

	@GetMapping("/followers")
	public ArrayList<Object> getFollowers(@PathVariable int userId) throws UserNotFoundException {
		return followService.getFollowers(userId);
	}

	@PostMapping("/followers/{followerId}")
	public User addFollower(@PathVariable int userId, @PathVariable int followerId)
			throws UserNotFoundException, InvalidDataException, InvalidFollowException {
		return followService.addFollower(userId, followerId);
	}

	@DeleteMapping("/followers/{followerId}")
	public User removeFollower(@PathVariable int userId, @PathVariable int followerId)
			throws UserNotFoundException, InvalidFollowException, InvalidDataException {
		return followService.removeFollower(userId, followerId);
	}

	@GetMapping("/followings")
	public ArrayList<Object> getFollowings(@PathVariable int userId) throws UserNotFoundException {
		return followService.getFollowings(userId);
	}

	@PostMapping("/followings/{followingId}")
	public User addFollowing(@PathVariable int userId, @PathVariable int followingId)
			throws UserNotFoundException, InvalidDataException, InvalidFollowException {
		return followService.addFollowing(userId, followingId);
	}

	@DeleteMapping("/followings/{followingId}")
	public User removeFollowing(@PathVariable int userId, @PathVariable int followingId)
			throws UserNotFoundException, InvalidFollowException, InvalidDataException {
		return followService.removeFollowing(userId, followingId);
	}

}
