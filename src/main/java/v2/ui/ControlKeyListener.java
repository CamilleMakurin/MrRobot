package v2.ui;

import v2.action.producer.ControlKey;
import v2.exception.GenericException;
import v2.workflow.WorkflowService;

public class ControlKeyListener {

    private static ControlKeyListener instance;

    public static ControlKeyListener getInstance() {
        if (instance == null) {
            instance = new ControlKeyListener();
        }
        return instance;
    }

    public void processControlKeyPress(ControlKey controlKey) throws GenericException {
        WorkflowService workflowService = WorkflowService.getInstance();
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
