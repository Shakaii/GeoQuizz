package geoquizz.backoffice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class NotFound extends RuntimeException {
    private static final long serialVersionUID = 12344567890L;

    public NotFound(String message) {
        super(message);
    }

    public NotFound(String message, Throwable cause) {
        super(message, cause);
    }


}