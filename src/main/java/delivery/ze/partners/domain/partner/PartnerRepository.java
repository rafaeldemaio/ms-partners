package delivery.ze.partners.domain.partner;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

public interface    PartnerRepository extends MongoRepository<Partner, String>, PartnerRepositoryCustom {

    @Query(value = "{'document': ?0 }", fields = "{'id': 1}")
    Optional<Partner> findByDocument(String document);
}

interface PartnerRepositoryCustom {
    Page<Partner> searchFromCoverageArea(Point point, Pageable pageable);

    Optional<Partner> searchFromCoverageArea(Point point);
}

@RequiredArgsConstructor
class PartnerRepositoryImpl implements PartnerRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Partner> searchFromCoverageArea(final Point point, final Pageable pageable) {

        final org.springframework.data.mongodb.core.query.Query query = buildQuery(point).with(pageable);
        List<Partner> partners = mongoTemplate.find(query, Partner.class);
        return PageableExecutionUtils.getPage(partners, pageable,
            () -> mongoTemplate.count(query.limit(-1).skip(-1), Partner.class));
    }

    @Override
    public Optional<Partner> searchFromCoverageArea(final Point point) {

        final org.springframework.data.mongodb.core.query.Query query = buildQuery(point);
        Partner partner = mongoTemplate.findOne(query, Partner.class);
        return Optional.ofNullable(partner);
    }

    private org.springframework.data.mongodb.core.query.Query buildQuery(final Point point) {

        return new org.springframework.data.mongodb.core.query.Query(
            new Criteria().where("coverageArea").intersects(new GeoJsonPoint(point))
            .and("address").nearSphere(point).maxDistance(0.503712240453784));
    }

}

