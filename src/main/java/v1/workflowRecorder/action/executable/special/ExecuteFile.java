package v1.workflowRecorder.action.executable.special;

import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.configuration.ConfigService;
import v1.workflowRecorder.constant.ActionAttribute;
import v1.workflowRecorder.file.FileTool;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class ExecuteFile implements ExecutableAction {

    private String name;
    private Map<ActionAttribute, String> attributes;

    public ExecuteFile() {
    }

    public ExecuteFile(String name, Map<ActionAttribute, String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    @Override
    public void execute(Robot robot) {
        Optional<String> filePathOpt = ConfigService.getConfigValue(name);
        String filePath = filePathOpt.orElse(attributes.get(ActionAttribute.EXECUTABLE_FILE_PATH));
        try {
            System.out.println("Executing workflowRecorder.file: " + filePath);
            FileTool fileTool = new FileTool();
            fileTool.executeEXEFile(filePath);
        } catch (IOException e) {
            System.out.println("Failed to launch workflowRecorder.file: " + filePath + " " + e.getMessage());
            e.printStackTrace();
        }

    }

}
