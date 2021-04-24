package delivery.ze.partners.restapi.data;

import delivery.ze.partners.domain.geojson.MultiPolygon;
import delivery.ze.partners.domain.geojson.Point;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartnersResponse {

    private String id;
    private String tradingName;
    private String ownerName;
    private String document;
    private MultiPolygon coverageArea;
    private Point address;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PartnersResponse that = (PartnersResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(tradingName, that.tradingName) && Objects.equals(ownerName,
            that.ownerName) && Objects.equals(document, that.document);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tradingName, ownerName, document);
    }

}
