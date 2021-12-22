package v1.workflowRecorder.context;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class WorkflowContext {

    private String contextId;
    private static Map<String, String> attributes;

    public static void initContext(){
        attributes = new HashMap<>();
    }

    public static String getAttribute(String key){
        return attributes.get(key);
    }

    public static void setAttribute(String key, String value){
        attributes.put(key, value);
    }
}
