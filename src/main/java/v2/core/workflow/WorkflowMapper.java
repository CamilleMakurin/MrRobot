package v2.core.workflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import v2.core.log.Log;
import v2.core.workflow.Workflow;

import java.io.IOException;

@Component
public class WorkflowMapper {

    private ObjectMapper mapper;

    public WorkflowMapper() {
        mapper = new ObjectMapper();
    }

    public String mapToJson(Workflow wf) {
        try {
            return mapper.writeValueAsString(wf);
        } catch (JsonProcessingException e) {
            Log.error("Failed to convert workflow to json: " + e.getMessage());
            return null;
        }
    }

    public Workflow readJson(String workflowJson) {
        try {
            return mapper.readValue(workflowJson, Workflow.class);
        } catch (IOException e) {
            Log.error("Failed to convert json to workflow: " + e.getMessage());
            return null;
        }
    }
}
