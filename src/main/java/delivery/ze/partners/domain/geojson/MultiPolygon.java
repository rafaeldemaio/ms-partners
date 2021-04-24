package delivery.ze.partners.domain.geojson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MultiPolygon {

    private String type;

    private List<List<List<List<Double>>>> coordinates;
}
