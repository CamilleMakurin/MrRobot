package v2.map.citydata;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityDataRepository {

    private CSVTool csvTool;

    @Autowired
    public CityDataRepository(CSVTool csvTool) {
        this.csvTool = csvTool;
    }

    public List<CityData> getCityData() {
        return csvTool.readCityData();
    }
}
