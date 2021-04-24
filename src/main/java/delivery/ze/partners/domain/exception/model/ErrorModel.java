package delivery.ze.partners.domain.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorModel {

    private String shortMessage;
    private String message;
    private Integer code;

    private Map<String, String> attributes;

    public ErrorModel(final String shortMessage, final String message, final Integer code) {
        this.shortMessage = shortMessage;
        this.message = message;
        this.code = code;
    }
}
