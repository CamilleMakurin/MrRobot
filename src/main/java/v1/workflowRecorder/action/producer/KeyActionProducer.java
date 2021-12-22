package v1.workflowRecorder.action.producer;

import v1.workflowRecorder.constant.ActionType;
import org.jnativehook.keyboard.NativeKeyEvent;
import v1.workflowRecorder.action.nativeaction.Action;
import v1.workflowRecorder.action.nativeaction.KeyAction;

public class KeyActionProducer {

    public Action createKeyPressAction(NativeKeyEvent e) {
        return new KeyAction(ActionType.PRESS, e.getKeyCode(), e.getWhen());
    }

    public Action createKeyReleaseAction(NativeKeyEvent e) {
        return new KeyAction(ActionType.RELEASE, e.getKeyCode(), e.getWhen());
    }

}
