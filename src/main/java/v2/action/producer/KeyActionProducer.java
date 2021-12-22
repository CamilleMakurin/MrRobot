package v2.action.producer;

import org.jnativehook.keyboard.NativeKeyEvent;
import v2.action.ActionType;
import v2.action.domain.Action;
import v2.action.domain.KeyAction;

public class KeyActionProducer {

    public Action createKeyPressAction(NativeKeyEvent e) {
        return new KeyAction(ActionType.PRESS, e.getKeyCode(), e.getWhen());
    }

    public Action createKeyReleaseAction(NativeKeyEvent e) {
        return new KeyAction(ActionType.RELEASE, e.getKeyCode(), e.getWhen());
    }

}
