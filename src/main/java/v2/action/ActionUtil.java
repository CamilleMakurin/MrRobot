package v2.action;

import v2.action.duplicate.DuplicateRemover;
import v2.wrapper.EventWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActionUtil {

    public static List<EventWrapper> mergeAndSort(List<EventWrapper>... wrappers) {
        List<EventWrapper> allWrappers = new ArrayList<>();
        for (List<EventWrapper> wrapperList : wrappers) {
            allWrappers.addAll(wrapperList);
        }
        DuplicateRemover.getInstance().removeSimultaneousActions(allWrappers);
        List<EventWrapper> orderedWrappers = allWrappers.stream().
                collect(Collectors.toMap(EventWrapper::getWhen, w -> w)).
                entrySet().stream().
                sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue).collect(Collectors.toList());
        return orderedWrappers;
    }
}
