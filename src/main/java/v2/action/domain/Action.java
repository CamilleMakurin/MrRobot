package v2.action.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import v2.action.ActionType;

import java.awt.*;
import java.util.Map;

@JsonDeserialize(using = ActionDeserializer.class)
public interface Action {

    void execute(Robot robot);

    void setAttributes(Map<ActionAttribute, String> attributes);

    long getWhen();

    long getDelay();

    ActionType getActionType();

    int getOrder();


}
