package  com.synchrony.assignment.controller.exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private RetryTemplate retryTemplate;

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return retryTemplate.execute(context -> {
            return "Retrying after failure: " + e.getMessage();
        });
    }
}
