package v2.core.action.duplicate;

import org.springframework.stereotype.Component;
import v2.core.wrapper.EventWrapper;

import java.util.List;

public class DuplicateRemover {

    public void removeSimultaneousActions(List<EventWrapper> wrappers) {
        //update actions that have same "when" value so there are no actions that happen at the same time
        //do it twice - dont remember why ???
        searchForDuplicates(wrappers);
        searchForDuplicates(wrappers);
    }

    private void searchForDuplicates(List<EventWrapper> all) {
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
