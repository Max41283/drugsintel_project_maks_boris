package drugsintel.accounting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class UserAccessDeniedException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8160877296165427872L;

	public UserAccessDeniedException(String msg) {
		super(msg);
	}

}
