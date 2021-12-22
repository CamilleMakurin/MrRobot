package v1.workflowRecorder.action.executable;

public class Util {


    public static boolean isDrag(int x, int nextX, int y, int nextY) {
        return notInRange(x, nextX) || notInRange(y, nextY);
    }

    private static boolean notInRange(int target, int rangeMiddle) {
        return target < rangeMiddle - 3 || target > rangeMiddle + 3;
    }
}
