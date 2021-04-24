package delivery.ze.partners.restapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartnersRequest {

    @NotBlank(message = "field mandatory")
    private String tradingName;

    @NotBlank(message = "field mandatory")
    private String ownerName;

    @NotBlank(message = "field mandatory")
    private String document;

    @NotNull(message = "field mandatory")
    private GeoJsonMultiPolygon coverageArea;

    @NotNull(message = "field mandatory")
    private GeoJsonPoint address;

}