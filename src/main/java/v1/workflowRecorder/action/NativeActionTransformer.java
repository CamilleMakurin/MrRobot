package v1.workflowRecorder.action;

import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.action.executable.mouse.*;
import v1.workflowRecorder.action.nativeaction.Action;
import v1.workflowRecorder.action.nativeaction.KeyAction;
import v1.workflowRecorder.action.nativeaction.MouseAction;
import v1.workflowRecorder.action.nativeaction.SpecialAction;
import v1.workflowRecorder.action.producer.SpecialActionProducer;
import v1.workflowRecorder.action.executable.keyboard.KeyPress;
import v1.workflowRecorder.action.executable.keyboard.KeyRelease;
import v1.workflowRecorder.constant.ActionType;
import v1.workflowRecorder.constant.MouseButton;

import java.util.ArrayList;
import java.util.List;

public class NativeActionTransformer {

    private SpecialActionProducer specialActionProducer = new SpecialActionProducer();

    public List<ExecutableAction> transformToExecutableActions(List<Action> actions) {

        List<ExecutableAction> result = new ArrayList<>();
        for (int i = 0; i < actions.size(); i++) {
            Action current = actions.get(i);
            if (current instanceof SpecialAction){
                result.add(createSpecialExecutableAction(current));
            } else if (current instanceof KeyAction) {
                result.add(createKeyActions((KeyAction) current));
            } else {
                result.add(createMouseActions((MouseAction) current, i, actions));
            }
        }
        return result;
    }

    private ExecutableAction createSpecialExecutableAction(Action current) {
        return specialActionProducer.produceAction((SpecialAction)current);
    }

    private ExecutableAction createMouseActions(MouseAction current, int i, List<Action> actions) {
        if (current.getActionType().equals(ActionType.MOVE)){
            return new MouseMove(current.getX(), current.getY());
        }
        MouseButton mouseButton = current.getMouseButton();
        int x = current.getX();
        int y = current.getY();
        Action next = getNextAction(actions, i);
        if (!(next instanceof MouseAction)) {
            return createMouseRelease(mouseButton, x, y, 1000);//sleepTime ?
        }
        MouseAction nextAction = (MouseAction) next;
        long sleepTime = getSleepTime(current, nextAction);
        return isPressAction(current.getActionType()) ?
                createMousePress(mouseButton, x, y, sleepTime, nextAction.getX(), nextAction.getY()) :
                createMouseRelease(mouseButton, x, y, sleepTime);
    }

    private long getSleepTime(MouseAction current, MouseAction nextAction) {
        return nextAction == null ? 1000 : nextAction.getWhen() - current.getWhen();
    }

    private ExecutableAction createMousePress(MouseButton mouseButton, int x, int y, long sleepTime, int newX, int newY) {
        return mouseButton == MouseButton.LEFT ? new LeftClick(x, y, sleepTime, newX, newY) : new RightClick(x, y, sleepTime, newX, newY);
    }

    private ExecutableAction createMouseRelease(MouseButton mouseButton, int x, int y, long sleepTime) {
        return mouseButton == MouseButton.LEFT ? new LeftRelease(x, y, sleepTime) : new RightRelease(x, y, sleepTime);
    }

    private Action getNextAction(List<Action> actions, int i) {
        int index = i + 1;
        if (actions.size() > index) {
            return actions.get(index);
        }
        return null;
    }

    private ExecutableAction createKeyActions(KeyAction current) {
        return isPressAction(current.getActionType()) ? new KeyPress(current.getKeyCode()) :
                new KeyRelease(current.getKeyCode());
    }

    private boolean isPressAction(ActionType current) {
        return current == ActionType.PRESS;
    }
}
