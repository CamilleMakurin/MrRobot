package v2.action.configuration;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SpecialActionConfiguration {

    private String workflowName;
    private int specialOrder;

    private Map<ActionAttribute, String> attributes;

    public static SpecialActionConfiguration builder() {
        return new SpecialActionConfiguration();
    }

    public SpecialActionConfiguration attribute(ActionAttribute attributeName, String value) {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
        }
        this.attributes.put(attributeName, value);
        return this;
    }

    public SpecialActionConfiguration specialOrder(int specialOrder) {
        this.specialOrder = specialOrder;
        return this;
    }

    public SpecialActionConfiguration workflowName(String workflowName) {
        this.workflowName = workflowName;
        return this;
    }
}
