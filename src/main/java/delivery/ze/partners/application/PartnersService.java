package delivery.ze.partners.application;

import delivery.ze.partners.domain.partner.Partner;
import delivery.ze.partners.restapi.data.PartnersRequest;

public interface PartnersService {

    String create(PartnersRequest request);

    Partner search(Double lng, Double lat);

    Partner search(String id);
}
