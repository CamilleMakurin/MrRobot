package v2.action.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import v2.action.ActionOrderSequenceGenerator;
import v2.action.ActionType;
import v2.constant.MouseButton;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonDeserialize(as = MouseAction.class)
public class MouseAction implements Action {

    private MouseButton mouseButton;
    private int x;
    private int y;
    private ActionType actionType;
    private long when;
    private long delay;
    private int order;


    public MouseAction(ActionType actionType, MouseButton mouseButton, int x, int y, long when, long delay) {
        this.actionType = actionType;
        this.mouseButton = mouseButton;
        this.x = x;
        this.y = y;
        this.when = when;
        this.order = ActionOrderSequenceGenerator.getNext();
        this.delay = delay;
    }

    @Override
    public void execute(Robot robot) {
        robot.mouseMove(x, y);
        //todo: this works only for right click
        if (ActionType.PRESS.equals(actionType)) {
            System.out.println("executing mouse press:  " + x + " " + y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(5);
        }
        if (ActionType.RELEASE.equals(actionType)) {
            System.out.println("executing mouse release:  " + x + " " + y);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
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
