package v2.core.workflow;

import org.springframework.stereotype.Component;
import v2.core.action.domain.Action;
import v2.core.action.domain.SpecialAction;
import v2.core.action.wfconfig.WorkflowConfig;
import v2.core.log.Log;

import java.awt.*;
import java.util.List;

@Component
public class WorkflowExecutor {

    private Robot robot;

    private void initRobot() {
        if (robot == null) {
            try {
                robot = new Robot();
            } catch (AWTException e) {
                Log.error("Failed to instantiate Robot: " + e.getMessage());
            }
        }
    }

    public void executeWorkflow(Workflow wf) {
        initRobot();
        List<Action> actionList = wf.getActionList();
        Action previousAction = null;
        for (Action action : actionList) {
            if (action instanceof SpecialAction) {
                action.execute(robot);
            } else {
                if (previousAction != null) {
                    robot.delay(Math.toIntExact(action.getDelay()));
                }
                previousAction = action;
                action.execute(robot);
            }
        }
    }
}
