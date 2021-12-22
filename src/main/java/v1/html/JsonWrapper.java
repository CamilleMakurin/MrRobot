package v1.html;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class JsonWrapper {

    public JsonWrapper() {
    }

    private VideoDetails videoDetails;

    @Override
    public String toString() {
        return "JsonWrapper{" +
                "videoDetails=" + videoDetails +
                '}';
    }
}
