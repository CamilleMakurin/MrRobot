package v1.workflowRecorder.action;

import v1.workflowRecorder.action.nativeaction.Action;
import v1.workflowRecorder.action.producer.ActionProducer;
import v1.workflowRecorder.event.EventType;
import v1.workflowRecorder.event.wrapper.EventWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ActionService {

    private ActionProducer actionProducer = new ActionProducer();

    public List<Action> createActions(List<EventWrapper> keyBoardEventWrappers, List<EventWrapper> mouseEventWrappers, boolean recordMouseMoves) {
        searchForDuplicates(keyBoardEventWrappers, mouseEventWrappers, 1);
        searchForDuplicates(keyBoardEventWrappers, mouseEventWrappers, 2);

        if (!recordMouseMoves) {
            Iterator<EventWrapper> iterator = mouseEventWrappers.iterator();
            while (iterator.hasNext()) {
                EventWrapper next = iterator.next();
                if (next.getType().equals(EventType.MOUSE_MOVE)) {
                    mouseEventWrappers.remove(next);
                }
            }
        }

        List<Action> actions = new ArrayList<>();
        Stream.concat(keyBoardEventWrappers.stream(), mouseEventWrappers.stream()).
                collect(Collectors.toMap(EventWrapper::getWhen, w -> w)).
                entrySet().stream().
                sorted(Map.Entry.comparingByKey()).forEach(entry -> produceAction(entry, actions));
        return actions;
    }

    private void searchForDuplicates(List<EventWrapper> keyBoardEventWrappers, List<EventWrapper> mouseEventWrappers, int runNumber) {
        List<EventWrapper> all = new ArrayList<>();
        all.addAll(keyBoardEventWrappers);
        all.addAll(mouseEventWrappers);
        for (int i = 0; i < all.size(); i++) {
            EventWrapper current = all.get(i);
            long when = current.getWhen();
            for (int i1 = 0; i1 < all.size(); i1++) {
                EventWrapper tocheck = all.get(i1);
                long toCheckWhen = tocheck.getWhen();
                if (i1 != i && toCheckWhen == when) {
                  //  Log.info("Run " + runNumber + ": found duplicate. Adding +1");
                    toCheckWhen = toCheckWhen + 1;
                    tocheck.setWhen(toCheckWhen);
                }
            }
        }
    }

    protected void produceAction(Map.Entry<Long, EventWrapper> entry, List<Action> actions) {
        actions.add(actionProducer.produce(entry.getValue()));
    }
}
