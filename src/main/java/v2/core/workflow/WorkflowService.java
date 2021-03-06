package v2.core.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import v2.core.action.ActionOrderSequenceGenerator;
import v2.core.action.ActionType;
import v2.core.action.ActionUtil;
import v2.core.action.domain.SpecialAction;
import v2.core.action.wfconfig.SpecialActionConfig;
import v2.core.action.wfconfig.WFConfigRepository;
import v2.core.action.wfconfig.WorkflowConfig;
import v2.core.exception.GenericException;
import v2.core.listener.KeyboardListener;
import v2.core.listener.MouseListener;
import v2.core.log.Log;
import v2.core.ApplicationContext;
import v2.core.action.domain.Action;
import v2.core.action.ActionService;
import v2.core.wrapper.EventWrapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class WorkflowService {

    private final ActionService actionService;
    private final WorkflowRepository workflowRepository;
    private final WorkflowExecutor workflowExecutor;
    private final WFConfigRepository wfConfigRepository;


    public static List<Action> actions = new ArrayList<>();

    @Autowired
    public WorkflowService(ActionService actionService, WorkflowRepository workflowRepository,
                           WorkflowExecutor workflowExecutor, WFConfigRepository wfConfigRepository) {
        this.actionService = actionService;
        this.workflowRepository = workflowRepository;
        this.workflowExecutor = workflowExecutor;
        this.wfConfigRepository = wfConfigRepository;
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
            context.pauseRecording();
            //set last event to create mouse move in paused recording gap
            markLastEvent();
        } else {
            context.startRecording();
            Log.info("Workflow recording started...");
        }
    }

    /**
     * Mark event as last before the recording was paused. Is used to generate mouse moves that were made during pause.
     * Only mouse events are considered
     */
    private void markLastEvent() {
        List<EventWrapper> eventWrappers = ActionUtil.mergeAndSort(MouseListener.getEvents());
        EventWrapper eventWrapper = eventWrappers.get(eventWrappers.size() - 1);
        eventWrapper.setIsLastBeforePause(true);
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
    public void stopWorkflowRecording() throws GenericException {
        ApplicationContext context = ApplicationContext.getContext();
        if (!context.isExecuting()) {
            Log.info("Saving workflow...");
            context.stopRecording();
            context.setFirstClickKeyPressMade(false);
            Workflow wf = workflowRepository.getWorkflow();
            if (wf != null) {
                List<Action> existingActions = wf.getActionList();
                if (!existingActions.isEmpty()) {
                    updateSequenceCounter(existingActions);
                    actions.addAll(existingActions);
                } else {
                    ActionOrderSequenceGenerator.resetSequence();
                }
            }
            wf = new Workflow();
            wf.setWfName(context.getWorkflowName());
            actions.addAll(actionService.createActionsFromNativeEvents(KeyboardListener.getEvents(), KeyboardListener.getSpecialActionEvents(), MouseListener.getEvents(), true));

            //TODO: this is a workaround for issue with action order. Possible fix to set all orders after actions are created.
            resetSpecialActionsOrder(actions);
            loadConfigs(wf, actions);

            wf.setActionList(actions);
            workflowRepository.saveWorkflow(wf);
            context.setNewWorkflow(true);
            Log.info("Workflow record saved...");
        }
    }

    private void loadConfigs(Workflow wf, List<Action> actions) {
        actions.stream().filter(a -> ActionType.SPECIAL == a.getActionType()).
                map(a -> (SpecialAction) a).
                forEach(a -> wfConfigRepository.getWorkflowConfig(wf.getWfName()).
                        getSpecActionConfigs().
                        stream().
                        filter(c -> c.getSpecialOrder() == a.getSpecialOrder()).
                        findFirst().ifPresent(c -> a.setAttributes(c.getAttributes())));
    }


    private void resetSpecialActionsOrder(List<Action> actions) {
        ActionOrderSequenceGenerator.resetSpecialSequence();
        for (Action action : actions) {
            if (ActionType.SPECIAL == action.getActionType()) {

            }
        }

    }

    /**
     * set sequence counter to the last action from existing workflow to continue workflow recording
     *
     * @param existingActions
     */
    private void updateSequenceCounter(List<Action> existingActions) {
        ActionOrderSequenceGenerator.setCurrent(existingActions.
                get(existingActions.size() - 1).getOrder());
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
