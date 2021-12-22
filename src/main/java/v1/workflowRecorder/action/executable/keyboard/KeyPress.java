package v1.workflowRecorder.action.executable.keyboard;

import v1.workflowRecorder.action.KeyMapping;
import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.constant.ActionAttribute;
import lombok.Getter;
import lombok.Setter;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.awt.*;
import java.util.Map;

@Getter
@Setter
public class KeyPress implements ExecutableAction {

    private int keyCode;

    public KeyPress() {
    }

    public KeyPress(int keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    public void execute(Robot robot) {
        robot.keyPress(KeyMapping.getAwtKeyCode(keyCode));
        robot.delay(400);
        System.out.println("Key Press " + keyCode + ", " + NativeKeyEvent.getKeyText(keyCode));

    }

    @Override
    public String getName() {
        return "unknown";
    }

    @Override
    public void setAttributes(Map<ActionAttribute, String> attributes) {
        System.out.println("KeyPress has not attributes");
    }
}
