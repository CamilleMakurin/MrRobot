package v1.workflowRecorder.action;

import v1.workflowRecorder.action.executable.ExecutableAction;

import java.awt.*;
import java.util.List;


public class ActionExecutor {

    private Robot robot;

    public ActionExecutor() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.out.println("Failed to instantiate Robot: " + e.getMessage());
        }
    }

    public void executeActions(List<ExecutableAction> actions){
        System.out.println("Started workflowRecorder.action execution...");
        for (ExecutableAction action : actions) {
            action.execute(robot);
        }
        System.out.println("Finished workflowRecorder.action execution...");
    }
}
