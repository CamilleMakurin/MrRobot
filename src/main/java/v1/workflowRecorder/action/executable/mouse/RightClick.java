package v1.workflowRecorder.action.executable.mouse;

import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.action.executable.Util;
import lombok.Getter;
import lombok.Setter;
import v1.workflowRecorder.action.Coordinates;
import v1.workflowRecorder.action.DragMoveProducer;
import v1.workflowRecorder.constant.ActionAttribute;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class RightClick implements ExecutableAction {

    private int x;
    private int y;
    private int nextX;
    private int nextY;
    private long sleepTime;

    public RightClick(int x, int y, long sleepTime, int nextX, int nextY) {
        this.x = x;
        this.y = y;
        this.nextX = nextX;
        this.nextY = nextY;
        this.sleepTime = sleepTime;
    }

    @Override
    public void execute(Robot robot) {
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        if (Util.isDrag(x, nextX, y, nextY)) {
            moveMouse(robot);
        }
        robot.delay(sleepTime < 100 ? 100 : (int) sleepTime);
        System.out.println("Right Click: sleep time: " + sleepTime);
    }

    private void moveMouse(Robot robot) {
        DragMoveProducer producer = new DragMoveProducer();
        List<Coordinates> coordinates = producer.produceSteps(x, nextX, y, nextY);
        coordinates.forEach(c -> robot.mouseMove(c.getX(), c.getY()));
    }

    @Override
    public String getName() {
        return "unknown";
    }

    @Override
    public void setAttributes(Map<ActionAttribute, String> attributes) {
        System.out.println("RightClick has not attributes");
    }
}
