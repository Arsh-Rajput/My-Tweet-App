package com.arsh.twitter.services;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arsh.twitter.exceptions.InvalidTweetStructureException;
import com.arsh.twitter.exceptions.TweetNotFoundException;
import com.arsh.twitter.exceptions.UserNotFoundException;
import com.arsh.twitter.models.Tweet;
import com.arsh.twitter.models.User;
import com.arsh.twitter.repositories.TweetRepository;
import com.arsh.twitter.utilities.FileUtility;

@Service
public class TweetService {

	@Autowired
	protected TweetRepository tweetRepository;

	@Autowired
	UserService userService;

	public Tweet getTweetById(int tweetId) throws TweetNotFoundException {

		Optional<Tweet> temp = tweetRepository.findById(tweetId);
		if (temp.isPresent())
			return temp.get();
		else
			throw new TweetNotFoundException("No tweet with specific id present");
	}

	public Tweet saveTweet(Tweet tweet) throws InvalidTweetStructureException {
		return tweetRepository.save(tweet);
	}

	public Tweet updateTweet(Tweet tweet) throws TweetNotFoundException, InvalidTweetStructureException {
		Optional<Tweet> t = tweetRepository.findById(tweet.getId());
		if (!t.isPresent())
			throw new TweetNotFoundException("No tweet with specific Id unable to update");
		return tweetRepository.save(tweet);
	}

	public void deleteTweetById(int tweetId) throws TweetNotFoundException {
		Optional<Tweet> t = tweetRepository.findById(tweetId);
		if (!t.isPresent())
			throw new TweetNotFoundException("No tweet with specific Id unable to delete");
		tweetRepository.deleteById(tweetId);
	}

	public void deleteAllTweet(User user) throws TweetNotFoundException {

		Set<Tweet> tweets = user.getTweets();
		if (tweets.size() == 0)
			throw new TweetNotFoundException("User have no Tweets associated");
		for (Tweet t : tweets) {
			tweetRepository.deleteById(t.getId());
		}
	}

	public String getTweetExceptionsLog() throws IOException {
		return FileUtility.readFromFile(FileUtility.getTweetExceptionFile());

	}

}
