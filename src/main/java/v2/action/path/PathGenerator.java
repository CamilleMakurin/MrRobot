package v2.action.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathGenerator {


    //y=3x^{\frac{1}{2}}

    private int MOVEMENT_STEP = 2;
    private int POSITIVE_DIRECTION = 1;
    private int NEGATIVE_DIRECTION = -1;
    private int NO_DIRECTION = 0;


    public List<Coordinates> generatePath(int xFrom, int yFrom, int xTo, int yTo) {
        int xMovementDirection = getDirection(xFrom, xTo);
        int yMovementDirection = getDirection(yFrom, yTo);

        if (xMovementDirection == 0 && yMovementDirection == 0) {
            return Collections.emptyList();
        }
        int xSteps = getNumberOfSteps(getDistance(xFrom, xTo));
        int ySteps = getNumberOfSteps(getDistance(yFrom, yTo));

        int requiredNumberOfSteps = Math.max(xSteps, ySteps);
        if (requiredNumberOfSteps == 0) {
            return Collections.emptyList();
        }
        List<Coordinates> coordinates = new ArrayList<>();
        do {

            xFrom = calculateCoordinate(xMovementDirection, xFrom, xTo);
            yFrom = calculateCoordinate(yMovementDirection, yFrom, yTo);
            coordinates.add(new Coordinates(xFrom, yFrom));
            requiredNumberOfSteps--;
        } while (requiredNumberOfSteps != 0);
        return coordinates;
    }

    /**
     * 0 to 6 -> 6/2 = 3 --> 6%2 ==0 ---> 3-1 steps required (2 and 4)
     * 0 to 7 -> 7/2 = 3 --> 7%2 != 0 ---> 3 steps required (2,4,6)
     *
     * @param distance
     * @return
     */
    private int getNumberOfSteps(int distance) {
        int steps = distance / MOVEMENT_STEP;
        return distance % MOVEMENT_STEP == 0 ? steps - 1 : steps;
    }

    private int calculateCoordinate(int movementDirection, int from, int to) {
        if (movementDirection == NO_DIRECTION) {
            return from;
        }
        return movementDirection == POSITIVE_DIRECTION ?
                Math.min(from + MOVEMENT_STEP, to) :
                Math.max(from - MOVEMENT_STEP, to);
    }

    /**
     * Returns 1 if from > to -> direction is positive and from should increase
     * Returns -1 if from < to -> direction is negative and from should decrease
     * Return o - if from == to -> there is no movement
     *
     * @param from
     * @param to
     * @return
     */
    private int getDirection(int from, int to) {
        if (from == to) {
            return NO_DIRECTION;
        }
        return from > to ? NEGATIVE_DIRECTION : POSITIVE_DIRECTION;
    }

    private int getDistance(int from, int to) {
        return from > to ? from - to : to - from;
    }
}
