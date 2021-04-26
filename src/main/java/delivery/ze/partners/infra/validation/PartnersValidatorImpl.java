package delivery.ze.partners.infra.validation;

import delivery.ze.partners.domain.exception.PartnersInvalidRequestException;
import delivery.ze.partners.domain.partner.PartnerRepository;
import delivery.ze.partners.restapi.data.PartnersRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PartnersValidatorImpl implements PartnersValidator {

    private final PartnerRepository partnerRepository;

    @Override
    public void validate(final PartnersRequest request) {
        Map<String, String> attributes = new HashMap<>();
        partnerRepository.findByDocument(request.getDocument()).ifPresent(partner ->
            attributes.put("document", "already registered"));

        GeoJsonMultiPolygon coverageArea = request.getCoverageArea();
        if (coverageArea.getCoordinates().isEmpty()) {
            attributes.put("coverageArea", "coordinates is invalid");
        }

        if (!attributes.isEmpty()) {
            throw new PartnersInvalidRequestException(attributes);
        }
    }

}
