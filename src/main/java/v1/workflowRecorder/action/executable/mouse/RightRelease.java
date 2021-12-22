package v1.workflowRecorder.action.executable.mouse;

import v1.workflowRecorder.action.executable.ExecutableAction;
import lombok.Getter;
import lombok.Setter;
import v1.workflowRecorder.constant.ActionAttribute;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Map;

@Setter
@Getter
public class RightRelease implements ExecutableAction {
    private long sleepTime;
    private int x;
    private int y;
    public RightRelease(int x, int y, long sleepTime) {
        this.x =x;
        this.y = y;
        this.sleepTime = sleepTime;
    }

    @Override
    public void execute(Robot robot) {
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        robot.delay((int) sleepTime);
        System.out.println("Right Release: sleep time: " + sleepTime);
    }

    @Override
    public String getName() {
        return "unknown";
    }

    @Override
    public void setAttributes(Map<ActionAttribute, String> attributes) {
        System.out.println("RightRelease has not attributes");
    }
}
