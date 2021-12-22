package v2.action.producer;

import org.jnativehook.mouse.NativeMouseEvent;
import v2.action.ActionType;
import v2.constant.MouseButton;
import v2.action.domain.Action;
import v2.action.domain.MouseAction;

public class MouseActionProducer {

    public Action createMouseMoveAction(NativeMouseEvent e) {
        return new MouseAction(ActionType.MOVE, null, e.getX(), e.getY(), e.getWhen());
    }

    public Action createMousePressAction(NativeMouseEvent e) {
        return new MouseAction(ActionType.PRESS, MouseButton.getButton(e.getButton()), e.getX(), e.getY(), e.getWhen());
    }

    public Action createMouseReleaseAction(NativeMouseEvent e) {
        return new MouseAction(ActionType.RELEASE, MouseButton.getButton(e.getButton()), e.getX(), e.getY(), e.getWhen());
    }

    public Action createMouseDragAction(NativeMouseEvent e) {
        return new MouseAction(ActionType.DRAG, null, e.getX(), e.getY(), e.getWhen());

    }
}
