package v1.workflowRecorder.trackinfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackInfo {

    private String trackName;
    private String trackAuthor;
    private String trackDuration;

    public TrackInfo() {
    }

    @JsonIgnore
    public String getSoundStripeFormatedName() {
        return (trackName + "_" + trackAuthor).replaceAll(" ", "_");
    }
}
