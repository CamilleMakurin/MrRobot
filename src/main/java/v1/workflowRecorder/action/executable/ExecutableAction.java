package v1.workflowRecorder.action.executable;

import v1.workflowRecorder.constant.ActionAttribute;

import java.awt.*;
import java.util.Map;

public interface ExecutableAction {

     void execute(Robot robot);

     String getName();

     void setAttributes(Map<ActionAttribute, String> attributes);




}
