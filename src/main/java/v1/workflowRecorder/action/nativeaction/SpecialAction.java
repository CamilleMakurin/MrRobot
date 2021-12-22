package v1.workflowRecorder.action.nativeaction;

import v1.workflowRecorder.constant.ActionAttribute;
import v1.workflowRecorder.constant.SpecialActionType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SpecialAction implements Action {

    private String name;
    private SpecialActionType actionType;
    private Map<ActionAttribute, String> attributes;

    public SpecialAction() {
    }

    public SpecialAction(String name, SpecialActionType actionType) {
        this.name = name;
        this.actionType = actionType;
        attributes = new HashMap<>();
    }
}
