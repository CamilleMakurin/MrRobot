package v2.action;


import v2.action.domain.Action;
import v2.action.duplicate.DuplicateRemover;
import v2.action.producer.ActionProducer;
import v2.exception.GenericException;
import v2.wrapper.EventType;
import v2.wrapper.EventWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActionService {

    private static ActionService instance;

    public static ActionService getInstance() {
        if (instance == null) {
            instance = new ActionService();
        }
        return instance;
    }

    private ActionProducer actionProducer = new ActionProducer();


    public List<Action> createActionsFromNativeEvents(List<EventWrapper> keyBoardEventWrappers,
                                                      List<EventWrapper> specialActionEvents, List<EventWrapper> mouseEventWrappers,
                                                      boolean recordMouseMoves) throws GenericException {

        //filter out mouse movements if mouse movement recording disabled
        mouseEventWrappers.removeIf(next -> !recordMouseMoves && next.getType().equals(EventType.MOUSE_MOVE));

        List<Action> actions = new ArrayList<>();
        //order by when action happened
        List<EventWrapper> allWrappers = ActionUtil.mergeAndSort(keyBoardEventWrappers, specialActionEvents, mouseEventWrappers);
        boolean firstRecordableActionFound = false;
        EventWrapper lastBeforePause = null;
        for (EventWrapper eventWrapper : allWrappers) {

            //check for first recordable action
            if (eventWrapper.getType().equals(EventType.MOUSE_PRESS) ||
                    eventWrapper.getType().equals(EventType.KEYBOARD_PRESS)) {
                firstRecordableActionFound = true;
            }

            //logic to generate mouse move action after pause
            if (lastBeforePause != null && isMouseWrapper(eventWrapper)) {
                actions.addAll(actionProducer.produceMoveActions(lastBeforePause, eventWrapper));
                lastBeforePause = null;
            }

            if (firstRecordableActionFound) {
                if (eventWrapper.isLastBeforePause()) {
                    lastBeforePause = eventWrapper;
                }
                produceAction(eventWrapper, actions);

            }
        }
        return actions;
    }

    private boolean isMouseWrapper(EventWrapper eventWrapper) {
        return eventWrapper.getType().equals(EventType.MOUSE_PRESS) ||
                eventWrapper.getType().equals(EventType.MOUSE_MOVE) ||
                eventWrapper.getType().equals(EventType.MOUSE_DRAG);
    }

    protected void produceAction(EventWrapper wrapper, List<Action> actions) throws GenericException {
        Action action = actionProducer.produceFromWrapper(wrapper);
        //TODO: replace with optional to remove null check OR move check of isRecordingControlKey press to this method
        /*Optional<Action> action1 = Optional.of(action);
        action1.ifPresent(actions::add);*/
        if (action != null) {
            actions.add(action);
        }
    }


}
