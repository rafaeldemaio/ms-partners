package delivery.ze.partners.restapi;

import delivery.ze.partners.domain.exception.PartnersInvalidRequestException;
import delivery.ze.partners.domain.exception.PartnersNotFoundException;
import delivery.ze.partners.domain.exception.model.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorModel handleValidationExceptions(final MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ErrorModel("Validation failed", "Validation failed for PartnersRequest",
            HttpStatus.BAD_REQUEST.value(), errors);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PartnersNotFoundException.class)
    public ErrorModel handleNotFoundException(final PartnersNotFoundException ex) {
        return ex.errorModel();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PartnersInvalidRequestException.class)
    public ErrorModel handleInvalidRequestException(final PartnersInvalidRequestException ex) {
        return ex.errorModel();
    }
}
