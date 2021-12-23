package v2.action;


import v2.action.domain.Action;
import v2.action.producer.ActionProducer;
import v2.wrapper.ControlKeyEventWrapper;
import v2.wrapper.EventType;
import v2.wrapper.EventWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActionService {

    private static ActionService instance;

    public static ActionService getInstance() {
        if (instance == null) {
            instance = new ActionService();
        }
        return instance;
    }

    private ActionProducer actionProducer = new ActionProducer();

    public List<Action> createActionsFromNativeEvents(List<EventWrapper> keyBoardEventWrappers, List<EventWrapper> mouseEventWrappers, List<EventWrapper> controlEvents, boolean recordMouseMoves) {
        removeSimultaneousActions(keyBoardEventWrappers, mouseEventWrappers);

        //filter out mouse movements if mouse movement recording disabled
        mouseEventWrappers.removeIf(next -> !recordMouseMoves && next.getType().equals(EventType.MOUSE_MOVE));

        List<Action> actions = new ArrayList<>();
        List<EventWrapper> allWrappers = new ArrayList<>();
        allWrappers.addAll(keyBoardEventWrappers);
        allWrappers.addAll(controlEvents);
        allWrappers.addAll(mouseEventWrappers);
        //order by when action happened
        List<EventWrapper> orderedWrappers = allWrappers.stream().
                collect(Collectors.toMap(EventWrapper::getWhen, w -> w)).
                entrySet().stream().
                sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue).collect(Collectors.toList());


        //filter all mouse movements before first click oo first button press
        //todo:
        //recalculate when if recording was paused based on control events
        boolean firstRecordableActionFound = false;

        orderedWrappers = removeControlEventTimeGaps(orderedWrappers);

        for (EventWrapper eventWrapper : orderedWrappers) {
            if (eventWrapper.getType().equals(EventType.MOUSE_PRESS) ||
                    eventWrapper.getType().equals(EventType.KEYBOARD_PRESS)) {
                firstRecordableActionFound = true;
            }
            if (firstRecordableActionFound) {

                produceAction(eventWrapper, actions);
            }
        }
        return actions;
    }

    private List<EventWrapper> removeControlEventTimeGaps(List<EventWrapper> orderedWrappers) {
        EventWrapper pauseRecordEventWrapper = null;
        EventWrapper previousWrapper = null;
        long deltaOne = 0;
        long deltaTwo = 0;

        for (EventWrapper currentWrapper : orderedWrappers) {
            if (currentWrapper instanceof ControlKeyEventWrapper) {
                //pause record button pressed
                if (pauseRecordEventWrapper == null) {
                    pauseRecordEventWrapper = currentWrapper;
                    deltaOne = deltaOne == 0 ? pauseRecordEventWrapper.getWhen() - previousWrapper.getWhen() :
                            deltaOne + pauseRecordEventWrapper.getWhen() - previousWrapper.getWhen();
                } else {
                    deltaTwo = deltaTwo == 0 ? currentWrapper.getWhen() - pauseRecordEventWrapper.getWhen() :
                            deltaTwo + currentWrapper.getWhen() - pauseRecordEventWrapper.getWhen();
                    pauseRecordEventWrapper = null;
                }
            } else {
                previousWrapper = currentWrapper;
            }
        }
        //todo: migth also filter out special action event wrappers
        return orderedWrappers.stream().filter(w->!(w instanceof ControlKeyEventWrapper)).collect(Collectors.toList());

    }

    protected void produceAction(EventWrapper wrapper, List<Action> actions) {
        actions.add(actionProducer.produce(wrapper));
    }

    private void removeSimultaneousActions(List<EventWrapper> keyBoardEventWrappers, List<EventWrapper> mouseEventWrappers) {
        //update actions that have same when value so there are no actions that happen at the same time
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
