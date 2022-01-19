package v2.core.action.domain.special;

import v2.core.constant.ActionAttribute;

import java.awt.*;
import java.util.Map;

public interface SpecialActionExecutor {

    void execute(Robot r, Map<ActionAttribute, String> attributes);


}
