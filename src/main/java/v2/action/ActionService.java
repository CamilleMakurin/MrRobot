package v2.action;


import v2.action.domain.Action;
import v2.action.producer.ActionProducer;
import v2.wrapper.EventType;
import v2.wrapper.EventWrapper;

import java.util.ArrayList;
import java.util.List;

public class ActionService {

    private static ActionService instance;

    public static ActionService getInstance() {
        if (instance == null) {
            instance = new ActionService();
        }
        return instance;
    }

    private ActionProducer actionProducer = new ActionProducer();


    public List<Action> createActionsFromNativeEvents(List<EventWrapper> keyBoardEventWrappers, List<EventWrapper> specialActionEvents, List<EventWrapper> mouseEventWrappers, boolean recordMouseMoves) {
        removeSimultaneousActions(keyBoardEventWrappers, mouseEventWrappers);

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

    protected void produceAction(EventWrapper wrapper, List<Action> actions) {
        actions.add(actionProducer.produceFromWrapper(wrapper));
    }

    private void removeSimultaneousActions(List<EventWrapper> keyBoardEventWrappers, List<EventWrapper> mouseEventWrappers) {
        //update actions that have same "when" value so there are no actions that happen at the same time
        //do it twice - dont remember why ???
        searchForDuplicates(keyBoardEventWrappers, mouseEventWrappers);
        searchForDuplicates(keyBoardEventWrappers, mouseEventWrappers);
    }

    private void searchForDuplicates(List<EventWrapper> keyBoardEventWrappers, List<EventWrapper> mouseEventWrappers) {
        List<EventWrapper> all = new ArrayList<>();
        all.addAll(keyBoardEventWrappers);
        all.addAll(mouseEventWrappers);
        for (int i = 0; i < all.size(); i++) {
            EventWrapper current = all.get(i);
            long when = current.getWhen();
            for (int j = 0; j < all.size(); j++) {
                EventWrapper tocheck = all.get(j);
                long toCheckWhen = tocheck.getWhen();
                if (j != i && toCheckWhen == when) {
                    //  Log.info("Run " + runNumber + ": found duplicate. Adding +1");
                    toCheckWhen = toCheckWhen + 1;
                    tocheck.setWhen(toCheckWhen);
                }
            }
        }
    }


}
