package v2.map.citydata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import v2.core.log.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVTool {

    private final String csvFilePath = "C:\\Dev\\projects\\MouseRobot\\working_directory\\citydata\\citydata.csv";

    private final ObjectMapper mapper = new ObjectMapper();

    public List<CityData> readCityData() {
        List<CityData> result = new ArrayList<>();
        try {
            Path path = Paths.get(csvFilePath);
            for (String line : Files.readAllLines(path)) {
                result.add(mapper.readValue(line, CityData.class));
            }
        } catch (IOException e) {
            Log.error("Failed to read file " + csvFilePath);
        }
        return result;
    }
}
