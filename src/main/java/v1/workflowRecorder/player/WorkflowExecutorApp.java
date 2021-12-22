package v1.workflowRecorder.player;

import v1.components.ImageDownloader;
import v1.components.TrackDownloader;
import v1.components.VideoCreator;
import v1.workflowRecorder.constant.TrackType;
import v1.workflowRecorder.file.FileTool;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class WorkflowExecutorApp {

    private static final List<String> commands = Arrays.asList("createVideo", "downloadTrack");

    public static WorkflowExecutor executor = new WorkflowExecutor();
    public static ImageDownloader imageDownloader = new ImageDownloader();

    public static void main(String[] args) throws IOException {
        LocalDateTime startTime = LocalDateTime.now();
        System.out.println("start: " + startTime);

        downloadTracks(52, TrackType.LOFI);
        //createVideo(Arrays.asList("Winter Came"));
        System.out.println("start time: " + startTime);
        System.out.println("end: " + LocalDateTime.now());
    }

    public static void downloadImages() {
        ImageDownloader.downloadImages(24);
    }

    public static void downloadTracks(int numberOfTracks, TrackType type) {
        TrackDownloader.downloadTracks(numberOfTracks, type);
    }

    public static void createVideo(List<String> videoNames) throws IOException {
        VideoCreator.createVideos(videoNames, TrackType.AMBIENT);
    }

    public static void checkFileValidity(TrackType type) {
        FileTool fileTool = new FileTool();
        fileTool.filesValid(type.getDownloadDirectoryPath());
    }



}
