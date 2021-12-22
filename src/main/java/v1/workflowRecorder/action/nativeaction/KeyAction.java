package v1.workflowRecorder.action.nativeaction;

import lombok.Getter;
import lombok.Setter;
import v1.workflowRecorder.constant.ActionType;

@Getter
@Setter
public class KeyAction implements Action {

    private int keyCode;
    private ActionType actionType;
    private long when;

    public KeyAction() {
    }

    public KeyAction(ActionType actionType, int keyCode, long when) {
        this.when = when;
        this.actionType = actionType;
        this.keyCode = keyCode;
    }
}
