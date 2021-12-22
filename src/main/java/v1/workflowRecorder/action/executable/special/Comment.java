package v1.workflowRecorder.action.executable.special;

import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.constant.ActionAttribute;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Map;

@Getter
@Setter
public class Comment implements ExecutableAction {

    private String name;
    private Map<ActionAttribute, String> attributes;

    public Comment() {
    }

    public Comment(String name, Map<ActionAttribute, String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    @Override
    public void execute(Robot robot) {
        System.out.println(attributes.get(ActionAttribute.COMMENT));
    }

}
