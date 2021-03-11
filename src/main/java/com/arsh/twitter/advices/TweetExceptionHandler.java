package com.arsh.twitter.advices;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.arsh.twitter.exceptions.InvalidTweetStructureException;
import com.arsh.twitter.exceptions.TweetNotFoundException;

@ControllerAdvice
public class TweetExceptionHandler {

	@ExceptionHandler(TweetNotFoundException.class)
	public ResponseEntity<Object> handleTweetNotFoundException(TweetNotFoundException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("Root cause", ex.getStackTrace()[0].getClassName());
		body.put("timestamp", LocalDateTime.now());
		body.put("Custom Error Code", "804");
		body.put("message", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(InvalidTweetStructureException.class)
	public ResponseEntity<Object> handleInvalidTweetStructureException(InvalidTweetStructureException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("Root cause", ex.getStackTrace()[0].getClassName());
		body.put("timestamp", LocalDateTime.now());
		body.put("Custom Error Code", "805");
		body.put("message", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

	}

}
