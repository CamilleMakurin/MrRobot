package v2.action.domain.special;

import v1.workflowRecorder.constant.ActionAttribute;
import v2.log.Log;

import java.awt.*;
import java.util.Map;


/**
 * Special action to to make workflow wait.
 *
 * There is a limit on how long the Robot can do delay at a single time.
 * Therefore if sleep time is longer that max single sleep it is split into
 * multiple delay iterations.
 */

public class SleepActionExecutor implements SpecialActionExecutor {

    public static final int MAX_SLEEP_TIME = 30000;

    @Override
    public void execute(Robot robot, Map<v2.constant.ActionAttribute, String> attributes) {

        String value = attributes.get(ActionAttribute.SLEEP_TIME);
        int sleepTime = Integer.parseInt(value);

        //70 000
        //70 000 % 30 000 = 2

        if (sleepTime >= MAX_SLEEP_TIME) {
            Log.debug("overall sleep time bigger than max robot sleep time. sleeping in steps");
            int timesToSleep = sleepTime / MAX_SLEEP_TIME;
            for (int i = 0; i < timesToSleep; i++) {
                robot.delay(MAX_SLEEP_TIME);
                sleepTime = sleepTime - MAX_SLEEP_TIME;
                Log.debug("sleeping step: " + i + 1);
            }
            Log.debug("executing rest of the sleep: " + sleepTime);
            robot.delay(sleepTime);
        } else {
            Log.debug("sleeping for " + sleepTime);
            robot.delay(sleepTime);
        }
    }
}
