package v2.core.action.wfconfig;

import lombok.Getter;
import lombok.Setter;
import v2.core.constant.ActionAttribute;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SpecialActionConfig {

    private int specialOrder;

    private Map<ActionAttribute, String> attributes;

    private SpecialActionConfig(int specialOrder) {
        this.specialOrder = specialOrder;
    }

    public static SpecialActionConfig order(int specialOrder) {
        return new SpecialActionConfig(specialOrder);
    }

    public SpecialActionConfig attribute(ActionAttribute attributeName, String value) {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
        }
        this.attributes.put(attributeName, value);
        return this;
    }

    public String getAttribute(ActionAttribute attribute) {
        return attributes.get(attribute);
    }
}
