package com.arsh.twitter.advices;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.arsh.twitter.exceptions.InvalidDataException;
import com.arsh.twitter.exceptions.InvalidFollowException;
import com.arsh.twitter.exceptions.UserNotFoundException;

@ControllerAdvice
public class UserExceptionHandler {
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("Root cause", ex.getStackTrace()[0].getClassName());
		body.put("timestamp", LocalDateTime.now());
		body.put("Custom Error Code", "604");
		body.put("message", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<Object> handleInvalidDataException(InvalidDataException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("Root cause", ex.getStackTrace()[0].getClassName());
		body.put("timestamp", LocalDateTime.now());
		body.put("Custom Error Code", "600");
		body.put("message", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidFollowException.class)
	public ResponseEntity<Object> handleInvalidFollowException(InvalidFollowException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("Root cause", ex.getStackTrace()[0].getClassName());
		body.put("timestamp", LocalDateTime.now());
		body.put("Custom Error Code", "700");
		body.put("message", ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

}
