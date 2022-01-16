package v2.action.domain.special;

import v2.constant.ActionAttribute;
import v2.files.FileTool;
import v2.log.Log;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class ExecuteFileActionExecutor implements SpecialActionExecutor {


    @Override
    public void execute(Robot r, Map<ActionAttribute, String> attributes) {
        String filePath = attributes.get(ActionAttribute.EXECUTABLE_FILE_PATH);
        try {
            Log.info("Executing workflowRecorder.file: " + filePath);
            FileTool fileTool = new FileTool();
            fileTool.executeEXEFile(filePath);
        } catch (IOException e) {
            Log.info("Failed to launch file: " + filePath + " " + e.getMessage());
            e.printStackTrace();
        }
    }
}
