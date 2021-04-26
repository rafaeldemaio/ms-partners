package delivery.ze.partners.domain.exception;

import delivery.ze.partners.domain.exception.model.ErrorModel;

import java.util.Map;

public class PartnersException extends RuntimeException {

    private final String shortMessage;
    private final Integer code;
    private Map<String, String> attributes;

    public PartnersException(final String message, final String shortMessage, final Integer code) {
        super(message);
        this.shortMessage = shortMessage;
        this.code = code;
    }

    public PartnersException(final String message, final String shortMessage, final Integer code, final Map<String, String> attributes) {
        super(message);
        this.attributes = attributes;
        this.shortMessage = shortMessage;
        this.code = code;
    }

    public ErrorModel errorModel() {
        return new ErrorModel(shortMessage, getMessage(), code, attributes);
    }
}
