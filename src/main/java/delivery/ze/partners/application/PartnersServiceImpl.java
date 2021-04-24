package delivery.ze.partners.application;

import delivery.ze.partners.domain.exception.PartnersNotFoundException;
import delivery.ze.partners.domain.partner.Partner;
import delivery.ze.partners.domain.partner.PartnerRepository;
import delivery.ze.partners.infra.converter.PartnersConverter;
import delivery.ze.partners.infra.validation.PartnersValidator;
import delivery.ze.partners.restapi.data.PartnersRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PartnersServiceImpl implements PartnersService {

    private final PartnerRepository repository;
    private final PartnersValidator validator;
    private final PartnersConverter converter;

    @Override
    public String create(final PartnersRequest partner) {
        validator.validate(partner);
        return repository.save(converter.toDomain(partner)).getId();
    }

    public Partner search(final Double lng, final Double lat) {
        Optional<Partner> partner = repository.searchFromCoverageArea(new Point(lng, lat));
        return partner.orElseThrow(() ->
            new PartnersNotFoundException("No partners found for specified location"));
    }

    @Override
    public Partner search(final String id) {
        Optional<Partner> partner = repository.findById(id);
        return partner.orElseThrow(() ->
            new PartnersNotFoundException("No partners found for specified id"));
    }

}
