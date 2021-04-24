package delivery.ze.partners.domain.partner;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Builder
@Document(collection = "partners")
public class Partner {

    @Id
    private String id;

    private String tradingName;

    private String ownerName;

    @Indexed(unique = true)
    private String document;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonMultiPolygon coverageArea;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point address;

    public void setAddress(final Point point) {
        this.address = point;
    }

    public void setId(final String id) {
        this.id = id;
    }

}
