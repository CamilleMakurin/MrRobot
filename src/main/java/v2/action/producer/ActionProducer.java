package v2.action.producer;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import v2.action.domain.Action;
import v2.action.path.Coordinates;
import v2.action.path.PathGenerator;
import v2.wrapper.EventWrapper;
import v2.wrapper.MouseEventWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ActionProducer {


    public static final int GENERATED_WHEN = 1000;
    public static final int GENERATED_DELAY = 4;
    private KeyActionProducer keyActionProducer;
    private MouseActionProducer mouseActionProducer;
    private SpecialActionProducer specialActionProducer;

    public ActionProducer() {
        this.keyActionProducer = new KeyActionProducer();
        this.mouseActionProducer = new MouseActionProducer();
        this.specialActionProducer = new SpecialActionProducer();
    }

    public Action produceFromWrapper(EventWrapper wrapper) {
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

    public List<Action> produceMoveActions(EventWrapper lastBeforePause, EventWrapper eventWrapper) {

        PathGenerator pathGenerator = new PathGenerator();
        NativeMouseEvent last = (NativeMouseEvent) lastBeforePause.getNativeEvent();
        NativeMouseEvent next = (NativeMouseEvent) eventWrapper.getNativeEvent();
        List<Coordinates> coordinates = pathGenerator.generatePath(last.getX(), last.getY(), next.getX(), next.getY());
        if (coordinates.isEmpty()) {
            return Collections.emptyList();
        }
        return coordinates.stream().
                map(c -> mouseActionProducer.generateMouseMoveAction(c.getX(), c.getY(), GENERATED_WHEN, GENERATED_DELAY)).
                collect(Collectors.toList());
    }

    private boolean isSpecialActionInsertKey(EventWrapper wrapper) {
        return ControlKey.INSERT_SPECIAL_ACTION.getKeyCode() == ((NativeKeyEvent) wrapper.getNativeEvent()).getKeyCode();
    }

}
