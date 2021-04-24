package delivery.ze.partners.domain.geojson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    private String type;
    private List<Double> coordinates;
}
