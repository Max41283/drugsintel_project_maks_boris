package drugsintel.accounting.security.jwt.advice;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import drugsintel.accounting.exceptions.UserNotActiveException;

@RestControllerAdvice
public class ActiveControllerAdvice {
	
	@ExceptionHandler(value = UserNotActiveException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public ErrorMessage handleTokenRefreshException(UserNotActiveException ex, WebRequest request) {
		return new ErrorMessage(
	        HttpStatus.FORBIDDEN.value(),
			new Date(),
			ex.getMessage(),
			request.getDescription(false));
	}

}
