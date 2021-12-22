package v2.workflow;

import v2.action.ActionType;
import v2.action.domain.Action;
import v2.action.domain.SpecialAction;
import v2.log.Log;

import java.awt.*;
import java.util.List;

public class WorkflowExecutor {

    private Robot robot;
    private static WorkflowExecutor instance;

    public static WorkflowExecutor getInstance() {
        if (instance == null) {
            instance = new WorkflowExecutor();
        }
        return instance;
    }

    public WorkflowExecutor() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.out.println("Failed to instantiate Robot: " + e.getMessage());
        }
    }

    public void executeWorkflow(Workflow wf) {
        List<Action> actionList = wf.getActionList();
        Action previousAction = null;
        for (Action action : actionList) {
            if (action instanceof SpecialAction) {
                action.execute(robot);
            } else {
                if (previousAction != null) {
                    robot.delay(Math.toIntExact((action.getWhen() - previousAction.getWhen())));
                }
                previousAction = action;
                action.execute(robot);
            }
        }
    }
}