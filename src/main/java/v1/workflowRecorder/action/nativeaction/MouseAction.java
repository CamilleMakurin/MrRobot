package v1.workflowRecorder.action.nativeaction;

import lombok.Getter;
import lombok.Setter;
import v1.workflowRecorder.constant.ActionType;
import v1.workflowRecorder.constant.MouseButton;

@Getter
@Setter
public class MouseAction implements Action {

    private MouseButton mouseButton;
    private int x;
    private int y;
    private ActionType actionType;
    private long when;

    public MouseAction() {
    }

    public MouseAction(ActionType actionType, MouseButton mouseButton, int x, int y, long when) {
        this.actionType = actionType;
        this.mouseButton = mouseButton;
        this.x = x;
        this.y = y;
        this.when = when;
    }
}
