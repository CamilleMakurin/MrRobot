package v2.core.action.producer;

import org.jnativehook.keyboard.NativeKeyEvent;
import v2.core.action.ActionType;
import v2.core.action.domain.Action;
import v2.core.action.domain.KeyAction;
import v2.core.wrapper.EventWrapper;

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
