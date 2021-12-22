package v1.workflowRecorder.action.executable.mouse;

import lombok.Getter;
import lombok.Setter;
import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.constant.ActionAttribute;

import java.awt.*;
import java.util.Map;

@Getter
@Setter
public class MouseMove implements ExecutableAction {

    private int x;
    private int y;

    public MouseMove(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute(Robot robot) {
        robot.mouseMove(x, y);
        robot.delay(10);
        //Log.info("Moved mouse to " + x + ":" + y);
    }

    @Override
    public String getName() {
        return "unknown";
    }

    @Override
    public void setAttributes(Map<ActionAttribute, String> attributes) {

        //Log.info("LeftClick has not attributes");
    }

}
