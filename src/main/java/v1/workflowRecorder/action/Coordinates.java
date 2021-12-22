package v1.workflowRecorder.action;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coordinates {

    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y;

    }
}
