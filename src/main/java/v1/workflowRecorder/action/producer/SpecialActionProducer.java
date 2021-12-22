package v1.workflowRecorder.action.producer;

import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.action.executable.special.*;
import v1.workflowRecorder.action.nativeaction.SpecialAction;
import v1.workflowRecorder.constant.ActionAttribute;

import java.util.Map;

import static v1.workflowRecorder.constant.SpecialActionType.*;

public class SpecialActionProducer {

    public ExecutableAction produceAction(SpecialAction action) {
        Map<ActionAttribute, String> attributes = action.getAttributes();
        String name = action.getName();
        switch (action.getActionType()) {
            case COPY_TO_CLIPBOARD:
                return new CopyToClipboard(name, attributes);
            case SLEEP:
                return new Sleep(name, attributes);
            case EXECUTE_FILE:
                return new ExecuteFile(name, attributes);
            case COMMENT:
                return new Comment(name, attributes);
            case WRITE_FROM_CLIPBOARD:
                return new WriteFromClipboard(name, attributes);
        }
        return null;
    }
}
