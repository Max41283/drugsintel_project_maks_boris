package drugsintel.accounting.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.LOCKED)
public class UserNotActiveException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 6826436654358918772L;

    public UserNotActiveException(Long id) {
        super("User " + id + " not active");
    }

}
