package drugsintel.accounting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4318374917181504835L;

	public TokenRefreshException(String token, String message) {
		super(String.format("Failed for [%s]: %s", token, message));
	}

}
