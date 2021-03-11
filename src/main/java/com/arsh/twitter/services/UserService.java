package com.arsh.twitter.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arsh.twitter.exceptions.InvalidDataException;
import com.arsh.twitter.exceptions.UserNotFoundException;
import com.arsh.twitter.models.Tweet;
import com.arsh.twitter.models.User;
import com.arsh.twitter.repositories.TweetRepository;
import com.arsh.twitter.repositories.UserRepository;
import com.arsh.twitter.utilities.FileUtility;

@SuppressWarnings("deprecation")
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	TweetRepository tweetRepository;

	User user;

	@PostConstruct
	public void defaultValues() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse("1996-09-19", formatter);
		user = new User("Arsh Rajput", "@ARsh", date, "arsh.rajput@tcs.com", "7071421404");
		userRepository.save(user);
		Tweet tweet = new Tweet("My first tweet", "General", "This is my first tweet writer arsh");
		tweet.setUser(user);
		tweetRepository.save(tweet);
		user = new User("Garvita Dixit", "@Garivita", date, "garvita.dixit@tcs.com", "1234567890");
		userRepository.save(user);
	}

	public List<User> findAll() throws UserNotFoundException {
		List<User> users = userRepository.findAll();
		if (users.size() <= 0)
			throw new UserNotFoundException("There are no users in the database");
		return users;
	}

	public User findById(int id) throws UserNotFoundException {

		Optional<User> optional = userRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else
			throw new UserNotFoundException("No user with specific Id found");

	}

	public User save(User user) throws InvalidDataException {
		if (user == null)
			throw new InvalidDataException("Bad Request");

		validateUser(user);
		userDataExists(user);
		User temp = userRepository.save(user);
		return temp;
	}

	public User update(User user) throws UserNotFoundException, InvalidDataException {
		if (user == null)
			throw new UserNotFoundException("Invalid User details");
		if (user.getId() == 0)
			throw new UserNotFoundException("Invalid User id");
		Optional<User> temp = userRepository.findById(user.getId());
		if (!temp.isPresent())
			throw new UserNotFoundException("No User with specific id present in database try creating one");
		validateUser(user);
		User newUser = userRepository.save(user);
		return newUser;
	}

	public void deleteById(int id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent())
			throw new UserNotFoundException("User with user id: " + id + " does not exist.");
		Set<Tweet> tweets = user.get().getTweets();
		for (Tweet t : tweets) {
			tweetRepository.deleteById(t.getId());
		}
		userRepository.deleteById(id);
	}

	public void deleteAll() throws UserNotFoundException {
		List<User> users = userRepository.findAll();
		if (users.size() == 0)
			throw new UserNotFoundException("No users Present in the database");
		for (User x : users) {
			Set<Tweet> tweets = x.getTweets();
			for (Tweet t : tweets) {
				tweetRepository.deleteById(t.getId());
			}
		}
		userRepository.deleteAll();
	}

	private void validateUser(User user) throws InvalidDataException {

		String contactReg = "^([9876])(\\d){9}";
		String emailReg = "^([a-zA-Z])+([0-9]*)([@])((gmail.com)|(tcs.com)|(hotmail.com)|(usbank.com))$";

		Pattern p = Pattern.compile(contactReg);
		Matcher m = p.matcher(user.getContact());
		/*
		 * if(!m.find()) throw new
		 * InvalidDataException("Invalid Contact details provided");
		 * p=Pattern.compile(emailReg); m=p.matcher(user.getEmail());
		 *//*
			 * if(!m.find()) throw new
			 * InvalidDataException("Invalid Email Id only tcs,gmail,hotmail and usbank domain ids are allowed"
			 * );
			 */
	}

	private void userDataExists(User user) throws InvalidDataException {
		ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
		for (User x : users) {
			if (x.getEmail().equalsIgnoreCase(user.getEmail()))
				throw new InvalidDataException("User email Already registered");
			if (x.getContact().equalsIgnoreCase(user.getContact()))
				throw new InvalidDataException("User contact Already Used by another account");
			if (x.getHandle().equals(user.getHandle()))
				throw new InvalidDataException("User handle already taken");
		}
	}

	public String getUserExceptionsLog() throws IOException {
		return FileUtility.readFromFile(FileUtility.getUserExceptionFile());

	}

	public String getOperationsLog() {
		return FileUtility.readFromFile(FileUtility.getLoggerFile());

	}

}
