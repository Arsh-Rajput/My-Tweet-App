package com.arsh.twitter.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.arsh.twitter.exceptions.InvalidTweetStructureException;
import com.arsh.twitter.exceptions.TweetNotFoundException;
import com.arsh.twitter.exceptions.UserNotFoundException;
import com.arsh.twitter.models.Tweet;
import com.arsh.twitter.models.User;
import com.arsh.twitter.services.TweetService;
import com.arsh.twitter.services.UserService;

@RestController
@RequestMapping(value = "users/{id}")
public class TweetController {

	@Autowired
	protected TweetService tweetService;

	@Autowired
	protected UserService userService;

	@GetMapping("/tweets")
	public Set getAllTweetsByUserId(@PathVariable int id) throws UserNotFoundException {

		User user = userService.findById(id);
		return user.getTweets();
	}

	@GetMapping("/tweets/{tweetId}")
	public Tweet getTweetById(@PathVariable int id, @PathVariable int tweetId) throws TweetNotFoundException {
		return tweetService.getTweetById(tweetId);
	}

	@PostMapping("/tweets")
	public Tweet saveTweet(@Valid @RequestBody Tweet tweet, @PathVariable int id, Errors error)
			throws UserNotFoundException, InvalidDataException, InvalidTweetStructureException {
		if (error.hasErrors()) {
			String message = "";
			List<FieldError> list = error.getFieldErrors();
			for (FieldError f : list)
				message += f.getDefaultMessage() + ", ";
			throw new InvalidTweetStructureException(message);
		}
		User user = userService.findById(id);
		tweet.setUser(user);
		return tweetService.saveTweet(tweet);

	}

	@PutMapping("/tweets")
	public Tweet updateTweet(@Valid @RequestBody Tweet tweet, @PathVariable int id, Errors error)
			throws UserNotFoundException, InvalidDataException, InvalidTweetStructureException, TweetNotFoundException {
		if (error.hasErrors()) {
			String message = "";
			List<FieldError> list = error.getFieldErrors();
			for (FieldError f : list)
				message += f.getDefaultMessage() + ", ";
			throw new InvalidTweetStructureException(message);
		}

		User user = userService.findById(id);
		if (tweet.getId() == 0)
			throw new InvalidTweetStructureException("No Tweet Id is provided");
		tweet.setUser(user);
		return tweetService.updateTweet(tweet);
	}

	@DeleteMapping("/tweets/{tweetId}")
	public void deleteTweetById(@PathVariable int tweetId) throws TweetNotFoundException {
		tweetService.deleteTweetById(tweetId);
	}

	@DeleteMapping("/tweets")
	public void deleteAll(@PathVariable int id)
			throws UserNotFoundException, InvalidDataException, TweetNotFoundException {
		User user = userService.findById(id);
		tweetService.deleteAllTweet(user);

	}

}
