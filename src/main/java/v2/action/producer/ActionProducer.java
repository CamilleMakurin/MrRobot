package v2.action.producer;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import v2.action.domain.Action;
import v2.wrapper.EventWrapper;

public class ActionProducer {


    private KeyActionProducer keyActionProducer;
    private MouseActionProducer mouseActionProducer;
    private SpecialActionProducer specialActionProducer;

    public ActionProducer() {
        this.keyActionProducer = new KeyActionProducer();
        this.mouseActionProducer = new MouseActionProducer();
        this.specialActionProducer = new SpecialActionProducer();
    }

    public Action produce(EventWrapper wrapper) {
        switch (wrapper.getType()) {
            case MOUSE_MOVE:
                return mouseActionProducer.createMouseMoveAction(wrapper);
            case MOUSE_PRESS:
                return mouseActionProducer.createMousePressAction(wrapper);
            case MOUSE_RELEASE:
                return mouseActionProducer.createMouseReleaseAction(wrapper);
            case MOUSE_DRAG:
                return mouseActionProducer.createMouseDragAction(wrapper);
            case KEYBOARD_PRESS:
                return isSpecialActionInsertKey(wrapper) ?
                        specialActionProducer.produceAction(wrapper) :
                        keyActionProducer.createKeyPressAction(wrapper);
            case KEYBOARD_RELEASE:
                //ignore special action
                if (!isSpecialActionInsertKey(wrapper)) {
                    return keyActionProducer.createKeyReleaseAction(wrapper);
                }
        }
        return null;
    }

    private boolean isSpecialActionInsertKey(EventWrapper wrapper) {
        return ControlKey.INSERT_SPECIAL_ACTION.getKeyCode() == ((NativeKeyEvent) wrapper.getNativeEvent()).getKeyCode();
    }

}
