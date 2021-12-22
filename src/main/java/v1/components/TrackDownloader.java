package v1.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import v1.workflowRecorder.WorkflowExecutableActionProducer;
import v1.workflowRecorder.action.ActionExecutor;
import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.constant.Configuration;
import v1.workflowRecorder.constant.TrackType;
import v1.workflowRecorder.context.WorkflowContext;
import v1.workflowRecorder.file.FileTool;
import v1.workflowRecorder.trackinfo.TrackInfo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrackDownloader {


    private static List<String> numbers = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    private static WorkflowExecutableActionProducer actionProducer = new WorkflowExecutableActionProducer();
    private static ActionExecutor actionExecutor = new ActionExecutor();
    private static FileTool fileTool = new FileTool();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void downloadTracks(int numberOfTracks, TrackType trackType) {
        FileTool ft = new FileTool();
        if (!ft.audionFilesInDownloadsFolder()) {
            try {
                WorkflowContext.initContext();
                System.out.println("Starting track download");
                List<ExecutableAction> workflowActions = actionProducer.getWorkflowActions(Configuration.DOWNLOAD_TRACKS_WORKFLOW_PATH);
                List<TrackInfo> trackInfoList = new ArrayList<>();
                for (int i = 0; i < numberOfTracks; i++) {
                    actionExecutor.executeActions(workflowActions);
                    TrackInfo trackInfo = new TrackInfo();
                    trackInfo.setTrackDuration(WorkflowContext.getAttribute("trackDuration"));
                    trackInfo.setTrackAuthor(WorkflowContext.getAttribute("trackAuthor"));
                    trackInfo.setTrackName(WorkflowContext.getAttribute("trackName"));
                    trackInfoList.add(trackInfo);
                }
                System.out.println("Track downloadFinished");
                System.out.println("Writing track info...");
                FileWriter fl = new FileWriter(Configuration.TRACKS_DOWNLOAD_FOLDER + "\\trackinfo.txt", true);

                List<TrackInfo> corrupted = new ArrayList<>();
                for (TrackInfo trackInfo : trackInfoList) {
                    if (!startWithNumber(trackInfo.getTrackDuration())) {
                        corrupted.add(trackInfo);
                    } else {
                        fl.append(objectMapper.writeValueAsString(trackInfo));
                        fl.append("\n");
                    }
                }
                fl.close();
                System.out.println("Finished writing track info...");
                if (!corrupted.isEmpty()) {
                    corrupted.forEach(i -> {
                        try {
                            System.out.println("corrupted: " + objectMapper.writeValueAsString(i));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    });
                }

                //todo: move tracks to download folder
                //moveTracksToTracksFolder(trackType);
                fileTool.filesValid(trackType.getDownloadDirectoryPath());
            } catch (IOException e) {
                System.out.println("failed to read script workflowRecorder.file: " + e.getMessage());
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        } else {
            System.out.println("Track download finished with error: Downloads folder contains audio files");
        }
    }

    private static boolean startWithNumber(String trackDuration) {
        return numbers.stream().anyMatch(trackDuration::startsWith);
    }

    public static void moveTracksToTracksFolder(TrackType trackType) {
        FileTool ft = new FileTool();
        ft.getListOfFiles(trackType.getDownloadDirectoryPath()).
                stream().
                filter(fl -> fl.contains(".wav")).forEach(ft::moveTrackFromDownloads);
    }
}
