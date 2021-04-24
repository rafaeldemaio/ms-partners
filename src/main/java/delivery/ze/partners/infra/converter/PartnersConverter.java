package delivery.ze.partners.infra.converter;

import delivery.ze.partners.domain.partner.Partner;
import delivery.ze.partners.restapi.data.PartnersRequest;
import delivery.ze.partners.restapi.data.PartnersResponse;
import org.springframework.data.domain.Page;

public interface PartnersConverter {

    Partner toDomain(PartnersRequest request);

    Page<PartnersResponse> toResponse(Page<Partner> list);

    PartnersResponse toResponse(Partner search);

}
