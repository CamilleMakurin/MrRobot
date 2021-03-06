package v2.core.action.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import v1.workflowRecorder.action.KeyMapping;
import v2.core.action.ActionOrderSequenceGenerator;
import v2.core.action.ActionType;
import v2.core.constant.ActionAttribute;

import java.awt.*;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@JsonDeserialize(as = KeyAction.class)
public class KeyAction implements Action {

    private int keyCode;
    private ActionType actionType;
    private long when;
    private int order;
    private long delay;

    public KeyAction(ActionType actionType, int keyCode, long when, long delay) {
        this.when = when;
        this.actionType = actionType;
        this.keyCode = keyCode;
        this.order = ActionOrderSequenceGenerator.getNext();
        this.delay = delay;
    }

    @Override
    public void execute(Robot robot) {
        if (ActionType.PRESS.equals(actionType)) {
            robot.keyPress(KeyMapping.getAwtKeyCode(keyCode));
        } else {
            robot.keyRelease(KeyMapping.getAwtKeyCode(keyCode));
        }
    }

    @Override
    public void setAttributes(Map<ActionAttribute, String> attributes) {

    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }
}
