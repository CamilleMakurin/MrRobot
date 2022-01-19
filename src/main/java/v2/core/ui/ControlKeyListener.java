package v2.core.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import v2.core.action.producer.ControlKey;
import v2.core.exception.GenericException;
import v2.core.workflow.WorkflowService;

@Component
public class ControlKeyListener {

    private final WorkflowService workflowService;

    @Autowired
    public ControlKeyListener(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    public void processControlKeyPress(ControlKey controlKey) throws GenericException {
        switch (controlKey) {
            case SAVE_WORKFLOW:
                workflowService.stopWorkflowRecording();
                break;
            case EXECUTE_WORKFLOW:
                workflowService.executeWorkflowRecording();
                break;
            case START_PAUSE_RECORDING:
                workflowService.startWorkflowRecording();
                break;
        }
    }
}
