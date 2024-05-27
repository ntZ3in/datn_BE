package bookcarupdate.bookcar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CloudExceptionHandler {
    @ExceptionHandler(value = {CloudNotFoundException.class})
    public ResponseEntity<Object> handleCloudVendorNotFoundException
            (CloudNotFoundException cloudVendorNotFoundException)
    {
        CloudException cloudVendorException = new CloudException(
                cloudVendorNotFoundException.getMessage(),
                cloudVendorNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(cloudVendorException, HttpStatus.NOT_FOUND);
    }
}
