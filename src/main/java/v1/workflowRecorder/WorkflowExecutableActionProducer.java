package v1.workflowRecorder;

import com.fasterxml.jackson.databind.ObjectMapper;
import v1.workflowRecorder.action.NativeActionTransformer;
import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.action.nativeaction.Action;
import v1.workflowRecorder.action.nativeaction.KeyAction;
import v1.workflowRecorder.action.nativeaction.MouseAction;
import v1.workflowRecorder.action.nativeaction.SpecialAction;
import v1.workflowRecorder.constant.ActionAttribute;
import v1.workflowRecorder.constant.SpecialActionType;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowExecutableActionProducer {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static List<ExecutableAction> getWorkflowActions(String workflowPath) throws IOException {
        Path file = Paths.get(workflowPath);
        BufferedReader bufferedReader = Files.newBufferedReader(file);
        NativeActionTransformer transformer = new NativeActionTransformer();
        return transformer.transformToExecutableActions(readActions(bufferedReader));
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
