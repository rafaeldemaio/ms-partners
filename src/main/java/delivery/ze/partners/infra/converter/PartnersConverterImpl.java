package delivery.ze.partners.infra.converter;

import delivery.ze.partners.domain.geojson.MultiPolygon;
import delivery.ze.partners.domain.geojson.Point;
import delivery.ze.partners.domain.partner.Partner;
import delivery.ze.partners.restapi.data.PartnersRequest;
import delivery.ze.partners.restapi.data.PartnersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PartnersConverterImpl implements PartnersConverter {

    @Override
    public Partner toDomain(final PartnersRequest request) {

        return Partner.builder()
            .address(getPointORM(request.getAddress()))
            .coverageArea(request.getCoverageArea())
            .document(request.getDocument())
            .ownerName(request.getOwnerName())
            .tradingName(request.getTradingName())
            .build();
    }

    private org.springframework.data.geo.Point getPointORM(final @NotNull(message = "field mandatory") GeoJsonPoint geoJsonPoint) {
        return new org.springframework.data.geo.Point(geoJsonPoint.getCoordinates().get(0), geoJsonPoint.getCoordinates().get(1));
    }

    @Override
    public Page<PartnersResponse> toResponse(final Page<Partner> documents) {
        List<PartnersResponse> responsesList = new ArrayList<>();

        documents.forEach(partner -> responsesList.add(toResponse(partner)));
        return new PageImpl<>(responsesList, documents.getPageable(), documents.getTotalElements());
    }

    @Override
    public PartnersResponse toResponse(final Partner partner) {
        return new PartnersResponse(partner.getId(), partner.getTradingName(), partner.getOwnerName(), partner.getDocument(),
            coverageAreaFromORM(partner.getCoverageArea()), addressFromORM(partner.getAddress()));
    }

    private Point addressFromORM(final org.springframework.data.geo.Point point) {
        return new Point("Point", Arrays.asList(point.getX(), point.getY()));
    }

    private MultiPolygon coverageAreaFromORM(final GeoJsonMultiPolygon geoJsonMultiPolygon) {
        List<GeoJsonPolygon> coordinates = geoJsonMultiPolygon.getCoordinates();
        List multiPolygons = new ArrayList();

        for (final GeoJsonPolygon polygon : coordinates) {
            List<List> polygons = new ArrayList<>();
            for (final GeoJsonLineString line : polygon.getCoordinates()) {
                List<List> points = new ArrayList<>();
                for (final org.springframework.data.geo.Point point : line.getCoordinates()) {
                    points.add(Arrays.asList(point.getX(), point.getY()));
                }
                polygons.add(points);
            }
            multiPolygons.add(polygons);
        }
        return new MultiPolygon(geoJsonMultiPolygon.getType(), multiPolygons);
    }
}
