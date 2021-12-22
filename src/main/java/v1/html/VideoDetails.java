package v1.html;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class VideoDetails {

    public VideoDetails() {
    }

    public String shortDescription;
    public String videoId;
    public String title;
    public String lengthSeconds;
    public List<String> keywords;
    public String channelId;
    public boolean isOwnerViewing;
    public boolean isCrawlable;
    public Thumbnail thumbnail;
    public boolean useCipher;
    public double averageRating;
    public boolean allowRatings;
    public String viewCount;
    public String author;
    public boolean isLowLatencyLiveStream;
    public boolean isPrivate;
    public boolean isUnpluggedCorpus;
    public String latencyClass;
    public boolean isLiveContent;


    @Override
    public String toString() {
        return "VideoDetails{" +
                "shortDescription='" + shortDescription + '\'' +
                '}';
    }
}


