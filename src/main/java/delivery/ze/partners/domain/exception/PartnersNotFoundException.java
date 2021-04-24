package delivery.ze.partners.domain.exception;

import org.springframework.http.HttpStatus;

public class PartnersNotFoundException extends PartnersException {

    public PartnersNotFoundException(final String message) {
        super(message, "No partners found", HttpStatus.NOT_FOUND.value());
    }

}
