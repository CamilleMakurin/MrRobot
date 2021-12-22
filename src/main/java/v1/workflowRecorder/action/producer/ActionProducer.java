package v1.workflowRecorder.action.producer;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import v1.workflowRecorder.action.nativeaction.Action;

import v1.workflowRecorder.event.wrapper.EventWrapper;

public class ActionProducer {

    private KeyActionProducer keyActionProducer;
    private MouseActionProducer mouseActionProducer;

    public ActionProducer() {
        this.keyActionProducer = new KeyActionProducer();
        this.mouseActionProducer = new MouseActionProducer();
    }

    public Action produce(EventWrapper wrapper) {
        switch (wrapper.getType()) {
            case MOUSE_MOVE:
                return mouseActionProducer.createMouseMoveAction((NativeMouseEvent) wrapper.getNativeEvent());
            case MOUSE_PRESS:
                return mouseActionProducer.createMousePressAction((NativeMouseEvent)wrapper.getNativeEvent());
            case MOUSE_RELEASE:
                return mouseActionProducer.createMouseReleaseAction((NativeMouseEvent)wrapper.getNativeEvent());
            case KEYBOARD_PRESS:
                return keyActionProducer.createKeyPressAction((NativeKeyEvent)wrapper.getNativeEvent());
            case KEYBOARD_RELEASE:
                return keyActionProducer.createKeyReleaseAction((NativeKeyEvent)wrapper.getNativeEvent());
        }
        return null;
    }
}
