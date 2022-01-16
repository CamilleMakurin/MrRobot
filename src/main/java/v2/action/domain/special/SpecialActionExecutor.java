package v2.action.domain.special;

import v2.constant.ActionAttribute;

import java.awt.*;
import java.util.Map;

public interface SpecialActionExecutor {

    void execute(Robot r, Map<ActionAttribute, String> attributes);


}
