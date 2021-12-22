package v1.workflowRecorder.action.nativeaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SleepAction implements Action {
//          {"sleepTime":300}

    private int sleepTime;

    public SleepAction() {
    }

    public SleepAction(int sleepTime) {
        this.sleepTime = sleepTime;
    }
}
