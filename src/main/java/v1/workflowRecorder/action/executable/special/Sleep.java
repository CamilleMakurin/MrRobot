package v1.workflowRecorder.action.executable.special;

import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.constant.ActionAttribute;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Map;

@Getter
@Setter
public class Sleep implements ExecutableAction {

    public static final int MAX_SLEEP_TIME = 30000;
    private String name;
    private Map<ActionAttribute, String> attributes;

    public Sleep() {
    }

    public Sleep(String name, Map<ActionAttribute, String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    @Override
    public void execute(Robot robot) {
        String value = attributes.get(ActionAttribute.SLEEP_TIME);
        int sleepTime = Integer.parseInt(value);

        //70 000
        //70 000 % 30 000 = 2

        if (sleepTime >= MAX_SLEEP_TIME) {
            System.out.println("overall sleep time bigger than max robot sleep time. sleeping in steps");
            int timesToSleep = sleepTime / MAX_SLEEP_TIME;
            for (int i = 0; i < timesToSleep; i++) {
                robot.delay(MAX_SLEEP_TIME);
                sleepTime = sleepTime - MAX_SLEEP_TIME;
                System.out.println("sleeping step: " + i + 1);
            }
            System.out.println("executing rest of the sleep: " + sleepTime);
            robot.delay(sleepTime);
        } else {
            System.out.println("sleeping for " + sleepTime);
            robot.delay(sleepTime);

        }
    }
}
