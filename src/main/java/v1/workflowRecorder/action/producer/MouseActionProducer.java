package v1.workflowRecorder.action.producer;

import v1.workflowRecorder.constant.ActionType;
import org.jnativehook.mouse.NativeMouseEvent;
import v1.workflowRecorder.action.nativeaction.Action;
import v1.workflowRecorder.action.nativeaction.MouseAction;
import v1.workflowRecorder.constant.MouseButton;

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
}
