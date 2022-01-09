package v2.action.producer;

import org.jnativehook.keyboard.NativeKeyEvent;
import v2.action.ActionType;
import v2.action.domain.Action;
import v2.action.domain.KeyAction;
import v2.wrapper.EventWrapper;

public class KeyActionProducer {

    public Action createKeyPressAction(EventWrapper w) {
        NativeKeyEvent e = (NativeKeyEvent) w.getNativeEvent();
        return new KeyAction(ActionType.PRESS, e.getKeyCode(), e.getWhen(), w.getDelay());
    }

    public Action createKeyReleaseAction(EventWrapper w) {
        NativeKeyEvent e = (NativeKeyEvent) w.getNativeEvent();
        return new KeyAction(ActionType.RELEASE, e.getKeyCode(), e.getWhen(), w.getDelay());
    }

}
