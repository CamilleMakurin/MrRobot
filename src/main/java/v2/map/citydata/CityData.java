package v2.map.citydata;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityData {

    String cityName;
    String country;
    String latitude;
    String longitude;
    boolean mapGenerated;
}
