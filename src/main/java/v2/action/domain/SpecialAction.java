package v2.action.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jnativehook.keyboard.NativeKeyEvent;
import v2.action.ActionAttribute;
import v2.action.ActionOrderSequenceGenerator;
import v2.action.ActionType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@JsonDeserialize(as = SpecialAction.class)
public class SpecialAction implements Action {

    private ActionType actionType;
    private Map<ActionAttribute, String> attributes;
    private int order;
    private int specialOrder;
    private long when;
    private long delay;

    public SpecialAction(NativeKeyEvent e) {
        this.actionType = ActionType.SPECIAL;
        this.attributes = new HashMap<>();
        this.order = ActionOrderSequenceGenerator.getNext();
        this.specialOrder = ActionOrderSequenceGenerator.getNextSpecial();
        this.delay = 10;
    }

    @Override
    public void execute(Robot robot) {
        System.out.println("Special action  executing");
    }

    @Override
    public void setAttributes(Map<ActionAttribute, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public long getWhen() {
        return 0;
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }


}
