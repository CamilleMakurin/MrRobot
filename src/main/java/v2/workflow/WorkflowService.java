package v2.workflow;

import v2.action.ActionOrderSequenceGenerator;
import v2.listener.KeyboardListener;
import v2.listener.MouseListener;
import v2.log.Log;
import v2.ApplicationContext;
import v2.action.domain.Action;
import v2.action.ActionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WorkflowService {

    private static WorkflowService instance;
    private ActionService actionService;
    private WorkflowRepository workflowRepository;
    private WorkflowExecutor workflowExecutor;

    public static List<Action> actions = new ArrayList<>();

    public static WorkflowService getInstance() {
        if (instance == null) {
            instance = new WorkflowService(ActionService.getInstance(), WorkflowRepository.getInstance(), WorkflowExecutor.getInstance());
        }
        return instance;
    }

    public WorkflowService(ActionService actionService, WorkflowRepository workflowRepository, WorkflowExecutor workflowExecutor) {
        this.actionService = actionService;
        this.workflowRepository = workflowRepository;
        this.workflowExecutor = workflowExecutor;
    }

    public void startWorkflowRecording() {
        ApplicationContext context = ApplicationContext.getContext();
        if (context.isNewWorkflow()) {
            actions = new ArrayList<>();
            KeyboardListener.resetEvents();
            MouseListener.resetEvents();
            context.setNewWorkflow(false);
        }
        if (context.isRecording()) {
            //recording paused
            Log.info("Workflow recording paused...");

            context.setRecording(false);
        } else {
            context.setRecording(true);
            Log.info("Workflow recording started...");
        }
    }

    /**
     * When stop button is pressed the workflow gets saved.
     * If workflow with name inputed in the text field already exists the existing workflow gets updated
     * (new workflow gets created and old and new actions are added there).
     * To do so actions from existing workflow are read and put to actions list.
     * Then the order sequence gets set to last action order of existing workflow and new actions are created
     * with next sequence order.
     * If workflow does not exist - new workflow gets created
     */
    public void stopWorkflowRecording() {
        ApplicationContext context = ApplicationContext.getContext();
        if (!context.isExecuting()) {
            Log.info("Saving workflow...");
            context.setRecording(false);
            Workflow wf = workflowRepository.getWorkflow();
            if (wf != null) {
                List<Action> existingActions = wf.getActionList();
                if (!existingActions.isEmpty()) {
                    ActionOrderSequenceGenerator.setCurrent(existingActions.
                            get(existingActions.size() - 1).getOrder());
                    actions.addAll(existingActions);
                } else {
                    ActionOrderSequenceGenerator.resetSequence();
                }
            }
            wf = new Workflow();
            actions.addAll(actionService.createActionsFromNativeEvents(KeyboardListener.getEvents(), MouseListener.getEvents(),KeyboardListener.getControlEvents(), true));
            wf.setActionList(actions);
            workflowRepository.saveWorkflow(wf);
            context.setNewWorkflow(true);
            Log.info("Workflow record saved...");
        }
    }

    public void executeWorkflowRecording() {
        ApplicationContext context = ApplicationContext.getContext();
        if (!context.isExecuting()) {
            LocalDateTime executionStart = LocalDateTime.now();
            Log.info("Started workflow execution at: " + executionStart);
            context.setExecuting(true);
            workflowExecutor.executeWorkflow(workflowRepository.getWorkflow());
            context.setExecuting(false);
            Log.info("Start workflow finished. Started at: " + executionStart + " finished at: " + LocalDateTime.now());
        }
    }
}
