package delivery.ze.partners.infra.validation;

import delivery.ze.partners.restapi.data.PartnersRequest;

public interface PartnersValidator {

    void validate(PartnersRequest request);

}
