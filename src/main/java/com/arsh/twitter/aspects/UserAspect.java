package com.arsh.twitter.aspects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.arsh.twitter.models.User;
import com.arsh.twitter.utilities.ChronologyUtility;
import com.arsh.twitter.utilities.FileUtility;

@Aspect
@Component
public class UserAspect {

	@Around(value = "execution(* com.arsh.twitter.services.UserService.*(..))")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		LocalTime before = LocalTime.now();
		Object result = joinPoint.proceed();
		String timeTaken = String.valueOf(before.until(LocalTime.now(), ChronoUnit.MILLIS));
		String message = LocalTime.now() + ": " + joinPoint.getSignature() + " called at " + before + ", completed at "
				+ LocalTime.now() + " total time taken " + timeTaken + " mili seconds.";
		FileUtility.writeToFile(FileUtility.getLoggerFile(), message);

		return result;
	}

	@AfterThrowing(value = "execution(* com.arsh.twitter.services.UserService.*(..))", throwing = "exception")
	public void logException(JoinPoint joinPoint, Exception exception) throws IOException {
		Object[] obj = joinPoint.getArgs();

		if (obj.length > 0) {
			if (obj[0] instanceof User) {
				FileUtility.writeToFile(FileUtility.getUserExceptionFile(),
						LocalTime.now() + ": " + joinPoint.getSignature() + " generated Exception at "
								+ ChronologyUtility.getCurrentFormattedTime() + " for input " + obj[0].toString()
								+ ", Cause: " + exception.getMessage());

			} else {
				FileUtility.writeToFile(FileUtility.getUserExceptionFile(),
						LocalTime.now() + ": " + joinPoint.getSignature() + " generated Exception at "
								+ ChronologyUtility.getCurrentFormattedTime() + " for input " + obj[0] + ", Cause: "
								+ exception.getMessage());
			}
		} else
			FileUtility.writeToFile(FileUtility.getUserExceptionFile(),
					LocalTime.now() + ": " + joinPoint.getSignature() + " generated Exception at "
							+ ChronologyUtility.getCurrentFormattedTime() + ", Cause: " + exception.getMessage());

	}
}
