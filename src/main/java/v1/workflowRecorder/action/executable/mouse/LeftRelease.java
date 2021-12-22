package v1.workflowRecorder.action.executable.mouse;

import v1.workflowRecorder.action.executable.ExecutableAction;
import lombok.Getter;
import lombok.Setter;
import v1.workflowRecorder.constant.ActionAttribute;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Map;

@Getter
@Setter
public class LeftRelease implements ExecutableAction {

    private int x;
    private int y;
    private long sleepTime;

    public LeftRelease(int x, int y, long sleepTime) {
        this.x =x;
        this.y = y;
        this.sleepTime = sleepTime;
    }

    @Override
    public void execute(Robot robot) {
        robot.mouseMove(x, y);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay((int) sleepTime);
        System.out.println("Left Release: sleep time: " + sleepTime);
    }

    @Override
    public String getName() {
        return "unknown";
    }

    @Override
    public void setAttributes(Map<ActionAttribute, String> attributes) {
        System.out.println("LeftRelease has not attributes");
    }
}
