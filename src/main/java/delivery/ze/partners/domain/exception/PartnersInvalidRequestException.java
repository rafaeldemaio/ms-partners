package delivery.ze.partners.domain.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class PartnersInvalidRequestException extends PartnersException {

    public PartnersInvalidRequestException(final Map attributes) {
        super("Validation failed for PartnersRequest", "Validation failed", HttpStatus.BAD_REQUEST.value(), attributes);
    }

}
