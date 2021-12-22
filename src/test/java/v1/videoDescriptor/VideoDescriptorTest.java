package v1.videoDescriptor;

import org.junit.jupiter.api.Test;
import v1.videoDescriptor.VideoDescriptor;
import v1.workflowRecorder.trackinfo.TrackInfo;

import java.util.Arrays;
import java.util.List;

class VideoDescriptorTest {


    private VideoDescriptor descriptor = new VideoDescriptor();

    @Test
    void name() {

        List<String> paths = Arrays.asList("somePath\\tracks\\Distant_Grey_Hale_instrumental_LOSSLESS.wav",
                "Cavern_Stephen_Keech_instrumental_LOSSLESS.wav",
                "Resemblance_Be_Still_The_Earth_instrumental_LOSSLESS.wav");

        List<TrackInfo> trackInfos = Arrays.asList(
                createTrackInfo("Distant Grey", "Hale", "2:32"),
                createTrackInfo("Cavern Stephen", "Keech", "3:25"),
                createTrackInfo("Resemblance Be", "Still The Earth", "3:15")
        );

        List<String> timelineTags = descriptor.createTimelineTags(paths, trackInfos);
        System.out.println(timelineTags);
    }

    private TrackInfo createTrackInfo(String trackName, String author,  String duration) {
        TrackInfo ti = new TrackInfo();
        ti.setTrackAuthor(author);
        ti.setTrackName(trackName);
        ti.setTrackDuration(duration);
        return ti;
    }
}