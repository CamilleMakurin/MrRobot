package v2.core.action.domain.special;

import v2.core.constant.ActionAttribute;
import v2.core.files.FileTool;
import v2.core.log.Log;

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
