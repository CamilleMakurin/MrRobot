package v2.action.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    private String name;
    private ActionType actionType;
    private Map<ActionAttribute, String> attributes;
    private int order;
    private long when;

    public SpecialAction(String name) {
        this.name = name;
    }

    public SpecialAction(String name, ActionType actionType) {
        this.name = name;
        this.actionType = actionType;
        attributes = new HashMap<>();
        this.order = ActionOrderSequenceGenerator.getNext();
    }

    @Override
    public void execute(Robot robot) {
        System.out.println("Special action " + name + " executed.");
    }

    @Override
    public void setAttributes(Map<ActionAttribute, String> attributes) {

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
