package com.arsh.twitter.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arsh.twitter.exceptions.InvalidDataException;
import com.arsh.twitter.exceptions.InvalidFollowException;
import com.arsh.twitter.exceptions.UserNotFoundException;
import com.arsh.twitter.models.User;
import com.arsh.twitter.utilities.FileUtility;

@Service
public class FollowService {

	@Autowired
	UserService userService;

	public ArrayList<Object> getFollowers(int id) throws UserNotFoundException {

		User user = userService.findById(id);
		ArrayList<Integer> followers = user.getFollowers();
		HashMap<Integer, String> hs = new HashMap<>();
		ArrayList<Object> l = new ArrayList<>();
		for (int x : followers) {
			hs.clear();
			User user1 = userService.findById(x);
			hs.put(user1.getId(), user1.getName());
			l.add(hs);
		}
		return l;
	}

	public ArrayList<Object> getFollowings(int id) throws UserNotFoundException {
		User user = userService.findById(id);
		ArrayList<Integer> followings = user.getFollowers();
		HashMap<Integer, String> hs = new HashMap<>();
		ArrayList<Object> l = new ArrayList<>();
		for (int x : followings) {
			hs.clear();
			User user1 = userService.findById(x);
			hs.put(user1.getId(), user1.getName());
			l.add(hs);
		}
		return l;
	}

	public User addFollower(int userId, int followerId)
			throws UserNotFoundException, InvalidDataException, InvalidFollowException {
		if (userId == followerId) {
			throw new InvalidFollowException("Cannot follow self");
		}
		User user = userService.findById(userId);
		User follower = userService.findById(followerId);
		ArrayList<Integer> followerList = user.getFollowers();
		ArrayList<Integer> followingList = follower.getFollowings();
		if (followerList.contains(followerId)) {
			throw new InvalidFollowException("Follower Already present");
		}

		followerList.add(followerId);
		followingList.add(userId);
		user.setFollowers(followerList);
		follower.setFollowings(followingList);
		userService.update(user);
		userService.update(follower);

		return user;

	}

	public User removeFollower(int userId, int followerId)
			throws UserNotFoundException, InvalidFollowException, InvalidDataException {
		if (userId == followerId) {
			throw new InvalidFollowException("Cannot unfollow self");
		}
		User user = userService.findById(userId);
		User follower = userService.findById(followerId);
		ArrayList<Integer> followerList = user.getFollowers();
		ArrayList<Integer> followingList = follower.getFollowings();

		if (!followerList.contains(followerId)) {
			throw new InvalidFollowException("follower not present");
		}

		followerList.remove(Integer.valueOf(followerId));
		followingList.remove(Integer.valueOf(userId));
		user.setFollowers(followerList);
		follower.setFollowings(followingList);
		userService.update(user);
		userService.update(follower);
		return user;

	}

	public User addFollowing(int userId, int followingId)
			throws UserNotFoundException, InvalidDataException, InvalidFollowException {
		if (userId == followingId) {
			throw new InvalidFollowException("Cannot follow self");
		}
		User user = userService.findById(userId);
		User following = userService.findById(followingId);
		ArrayList<Integer> followingList = user.getFollowings();
		ArrayList<Integer> followerList = following.getFollowers();

		if (followingList.contains(followingId)) {
			throw new InvalidFollowException("Already following this user");
		}

		followerList.add(userId);
		followingList.add(followingId);
		user.setFollowings(followingList);
		following.setFollowers(followerList);
		userService.update(user);
		userService.update(following);

		return user;

	}

	public User removeFollowing(int userId, int followingId)
			throws UserNotFoundException, InvalidFollowException, InvalidDataException {
		if (userId == followingId) {
			throw new InvalidFollowException("Cannot unfollow self");
		}
		User user = userService.findById(userId);
		User following = userService.findById(followingId);
		ArrayList<Integer> followingList = user.getFollowings();
		ArrayList<Integer> followerList = following.getFollowers();

		if (!followingList.contains(followingId)) {
			throw new InvalidFollowException("You are already not following him");
		}

		followerList.remove(Integer.valueOf(userId));
		followingList.remove(Integer.valueOf(followingId));
		user.setFollowings(followingList);
		following.setFollowers(followerList);
		userService.update(user);
		userService.update(following);

		return user;

	}

	public String getFollowExceptionsLog() throws IOException {
		return FileUtility.readFromFile(FileUtility.getFollowExceptionFile());

	}

}
