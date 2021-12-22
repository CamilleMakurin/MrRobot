package v1.workflowRecorder.action;

import java.util.*;

public class DragMoveProducer {

    public static final String INCREASING = "INCREASING";
    public static final String DECREASING = "DECREASING";
    public static final String NOT_CHANGING = "NOT_CHANGING";

    public List<Coordinates> produceSteps(int x, int nextX, int y, int nextY) {
        List<Coordinates> steps = new ArrayList<>();
        String xDirection = isIncrease(x, nextX);
        String yDirection = isIncrease(y, nextY);
        int stepX = x;
        int stepY = y;
        do {
            stepX = increment(stepX, xDirection, nextX);
            stepY = increment(stepY, yDirection, nextY);
            steps.add(new Coordinates(stepX, stepY));
        } while (nextX != stepX || nextY != stepY);
        return steps;
    }

    private int increment(int step, String direction, int next) {
        if (direction.equals(NOT_CHANGING)) {
            return step;
        } else if (direction.equals(INCREASING)) {
            step = step + 3;
            return next - step <= 2 ? next : step;
        } else {
            step = step - 3;
            return step - next <= 2 ? next : step;
        }
    }

    private String isIncrease(int current, int next) {
        if (current < next) {
            return INCREASING;
        } else if (current > next) {
            return DECREASING;
        }
        return NOT_CHANGING;
    }
}
