package v2.core.action.producer;

import org.jnativehook.mouse.NativeMouseEvent;
import v2.core.action.ActionType;
import v2.core.constant.MouseButton;
import v2.core.action.domain.Action;
import v2.core.action.domain.MouseAction;
import v2.core.log.Log;
import v2.core.wrapper.EventWrapper;

public class MouseActionProducer {

    public Action createMouseMoveAction(EventWrapper w) {
        NativeMouseEvent e = (NativeMouseEvent) w.getNativeEvent();
        return new MouseAction(ActionType.MOVE, null, e.getX(), e.getY(), e.getWhen(), w.getDelay());
    }

    public Action createMousePressAction(EventWrapper w) {
        NativeMouseEvent e = (NativeMouseEvent) w.getNativeEvent();
        return new MouseAction(ActionType.PRESS, MouseButton.getButton(e.getButton()), e.getX(), e.getY(), e.getWhen(), w.getDelay());
    }

    public Action createMouseReleaseAction(EventWrapper w) {
        NativeMouseEvent e = (NativeMouseEvent) w.getNativeEvent();
        return new MouseAction(ActionType.RELEASE, MouseButton.getButton(e.getButton()), e.getX(), e.getY(), e.getWhen(), w.getDelay());
    }

    public Action createMouseDragAction(EventWrapper w) {
        NativeMouseEvent e = (NativeMouseEvent) w.getNativeEvent();
        return new MouseAction(ActionType.DRAG, null, e.getX(), e.getY(), e.getWhen(), w.getDelay());

    }

    public Action generateMouseMoveAction(int x, int y, int when, int delay) {
        return new MouseAction(ActionType.MOVE, null, x, y, when, delay);
    }
}
