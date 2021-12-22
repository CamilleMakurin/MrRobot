package v1.components;

import v1.preparation.InfraPreparator;
import v1.validation.Validator;
import v1.workflowRecorder.constant.Configuration;
import v1.workflowRecorder.constant.TrackType;
import v1.workflowRecorder.file.FileTool;
import v1.workflowRecorder.player.WorkflowExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VideoCreator {

    private static Validator validator = new Validator();
    private static InfraPreparator infraPreparator = new InfraPreparator();
    private static FileTool fileTool = new FileTool();
    private static WorkflowExecutor workflowExecutor = new WorkflowExecutor();


    public static void createVideos(List<String> videoNames, TrackType trackType) throws IOException {
        boolean enoughAudioFiles = validator.checkAudioTracksPresent(videoNames.size() * Configuration.NUMBER_OF_TRACKS_PER_VIDEO);
        boolean enoughImageFiles = validator.checkImagesPresent(videoNames.size() * Configuration.NUMBER_OF_IMAGES_PER_VIDEO);
        List<String> workingDirectories = new ArrayList<>();
        if (enoughAudioFiles && enoughImageFiles) {
            System.out.println("Enough audio and image tracks for execution");
            for (String videoName : videoNames) {
                String rootDirectoryPath = infraPreparator.createDirectories(videoName);
                workingDirectories.add(rootDirectoryPath);
                if (rootDirectoryPath != null) {
                    infraPreparator.moveAudioFiles(rootDirectoryPath);
                    infraPreparator.moveImageFiles(rootDirectoryPath);
                }
            }
            if (allReady(workingDirectories)) {
                workflowExecutor.executeWorkflows(workingDirectories);
            }
        } else {
            if (!enoughAudioFiles) {
                System.out.println("Not enough audio files");
            }
            if (!enoughImageFiles) {
                System.out.println("Not enough image files");
            }
        }
    }

    private static boolean allReady(List<String> workingDirectories) {
        List<String> errors = new ArrayList<>();
        for (String workingDirectory : workingDirectories) {
            boolean exists = fileTool.fileExists(workingDirectory + "\\source\\photo.jpg");
            if (!exists) {
                errors.add("photo file missing in source directory: " + workingDirectory);
            }
            List<String> listOfFiles = fileTool.getListOfFiles(workingDirectory + "\\foto");
            if (listOfFiles.isEmpty()) {
                errors.add("thumbnail source foto file missing in foto directory: " + workingDirectory);
            } else {
                if (listOfFiles.size() > 1) {
                    errors.add("in foto directory more than one file. Directory: " + workingDirectory);
                } else {
                    String fileName = listOfFiles.get(0);
                    if (!fileName.contains(".jpg") && !fileName.contains(".png")) {
                        errors.add("foto directory doesn't contain jpg or png files " + workingDirectory);
                    }
                }
            }
            List<String> listOfFiles1 = fileTool.getListOfFiles(workingDirectory + "\\tracks");
            if (listOfFiles1.size() < 17) {
                errors.add("tracks directory does not have enough tracks: " + workingDirectory);
            }
        }
        if (errors.isEmpty()) {
            System.out.println("all ready for workflow execution");
            return true;
        } else {
            System.out.println("directories are not ready for workflow execution \n Errors:");
            errors.forEach(System.out::println);
            return false;
        }
    }
}
