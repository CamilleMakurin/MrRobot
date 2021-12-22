package v1.workflowRecorder.action.nativeaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import v1.workflowRecorder.action.nativeaction.SpecialAction;
import v1.workflowRecorder.constant.ActionAttribute;
import v1.workflowRecorder.constant.SpecialActionType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class SpecialActionTest {


    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testParse() throws IOException {
        //{"name":"SpecialActionName","actionType":"COPY_TO_CLIPBOARD","attributes":{"VALUE_TO_COPY":"abc"}}
        SpecialAction action = new SpecialAction("SpecialActionName", SpecialActionType.COPY_TO_CLIPBOARD);
        Map<ActionAttribute,String> attributes = new HashMap<>();
        attributes.put(ActionAttribute.VALUE_TO_COPY, "abc");
        action.setAttributes(attributes);
        String s = objectMapper.writeValueAsString(action);
        System.out.println(s);
        SpecialAction specialAction = objectMapper.readValue(s, SpecialAction.class);
        System.out.println(specialAction);
    }

    @Test
    void testParse_onlyName() throws IOException {
        //{"name":"SpecialActionName","actionType":"COPY_TO_CLIPBOARD","attributes":{}}
        SpecialAction action = new SpecialAction("SpecialActionName", SpecialActionType.COPY_TO_CLIPBOARD);
        String s = objectMapper.writeValueAsString(action);
        System.out.println(s);
        SpecialAction specialAction = objectMapper.readValue(s, SpecialAction.class);
        System.out.println(specialAction);
    }
}