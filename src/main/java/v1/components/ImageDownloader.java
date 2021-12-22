package v1.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import v1.workflowRecorder.action.ActionExecutor;
import v1.workflowRecorder.action.NativeActionTransformer;
import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.action.nativeaction.Action;
import v1.workflowRecorder.action.nativeaction.KeyAction;
import v1.workflowRecorder.action.nativeaction.MouseAction;
import v1.workflowRecorder.action.nativeaction.SpecialAction;
import v1.workflowRecorder.constant.ActionAttribute;
import v1.workflowRecorder.constant.Configuration;
import v1.workflowRecorder.constant.SpecialActionType;
import v1.workflowRecorder.context.WorkflowContext;
import v1.workflowRecorder.file.FileTool;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ImageDownloader {

    private static ActionExecutor actionExecutor = new ActionExecutor();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void downloadImages(int numberOfImages) {
        FileTool ft = new FileTool();
        if (!ft.imagesInDownloadsFolder()) {
            try {
                WorkflowContext.initContext();
                System.out.println("Starting image download");
                List<ExecutableAction> workflowActions = getWorkflowActions(Configuration.DOWNLOAD_IMAGES_WORKFLOW_PATH);
                for (int i = 0; i < numberOfImages; i++) {
                    actionExecutor.executeActions(workflowActions);
                }
                System.out.println("waiting for download to finish: " + "120000");
                Thread.sleep(120000);
                System.out.println("Image downloadFinished");
                moveImagesToPhotoFolder();
            } catch (IOException | InterruptedException e) {
                System.out.println("failed to read script workflowRecorder.file: " + e.getMessage());
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        } else {
            System.out.println("Track download finished with error: Downloads folder contains audio files");
        }
    }

    private static List<ExecutableAction> getWorkflowActions(String workflowPath) throws IOException {
        Path file = Paths.get(workflowPath);
        BufferedReader bufferedReader = Files.newBufferedReader(file);
        NativeActionTransformer transformer = new NativeActionTransformer();
        return transformer.transformToExecutableActions(readActions(bufferedReader));
    }

    public static void moveImagesToPhotoFolder() {
        FileTool ft = new FileTool();
        ft.getListOfFiles(Configuration.DOWNLOADS_FOLDER).
                stream().
                filter(fl -> fl.contains(".jpg")).forEach(image->ft.moveFile(image, Configuration.DOWNLOADS_FOLDER, Configuration.IMAGE_DOWNLOAD_FOLDER));
    }

    private static List<Action> readActions(BufferedReader bufferedReader) throws IOException {
        List<Action> actions = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            actions.add(readValue(line));
        }
        return actions;
    }

    private static Action readValue(String line) throws IOException {
        if (line.startsWith("--")) {
            SpecialAction comment = new SpecialAction("comment", SpecialActionType.COMMENT);
            Map<ActionAttribute, String> attributes = new HashMap<>();
            attributes.put(ActionAttribute.COMMENT, line);
            comment.setAttributes(attributes);
            return comment;
        }
        if (line.contains("keyCode")) {
            //keyboard
            return objectMapper.readValue(line, KeyAction.class);
        } else if (line.contains("mouseButton")) {
            //mouse
            return objectMapper.readValue(line, MouseAction.class);
        } else {
            //special
            return objectMapper.readValue(line, SpecialAction.class);
        }
    }
}
