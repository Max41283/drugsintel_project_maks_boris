package drugsintel.accounting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UserNotActiveException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1920604860097976593L;

	public UserNotActiveException(String userName) {
		super(userName + " is not active");
	}

}
