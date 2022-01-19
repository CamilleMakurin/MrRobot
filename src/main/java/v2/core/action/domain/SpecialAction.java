package v2.core.action.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jnativehook.keyboard.NativeKeyEvent;
import v2.core.action.ActionOrderSequenceGenerator;
import v2.core.action.ActionType;
import v2.core.action.producer.ControlKey;
import v2.core.constant.ActionAttribute;

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
    private ControlKey controlKey;

    public SpecialAction(NativeKeyEvent e, ControlKey controlKey) {
        this.actionType = ActionType.SPECIAL;
        this.attributes = new HashMap<>();
        this.order = ActionOrderSequenceGenerator.getNext();
        this.specialOrder = ActionOrderSequenceGenerator.getNextSpecial();
        this.delay = 10;
        this.controlKey = controlKey;
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
