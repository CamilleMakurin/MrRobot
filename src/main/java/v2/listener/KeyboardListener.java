package v2.listener;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import v1.workflowRecorder.player.RecordAndPlay;
import v2.ApplicationContext;
import v2.action.producer.ControlKey;
import v2.log.Log;
import v2.ui.ControlKeyListener;
import v2.wrapper.ControlKeyEventWrapper;
import v2.wrapper.EventType;
import v2.wrapper.EventWrapper;
import v2.wrapper.KeyBoardEventWrapper;

import java.util.ArrayList;
import java.util.List;

public class KeyboardListener implements NativeKeyListener {

    private static List<EventWrapper> events = new ArrayList<>();
    private static List<EventWrapper> controlEvents = new ArrayList<>();

    public static void resetEvents() {
        events = new ArrayList<>();
        controlEvents = new ArrayList<>();
    }

    public void nativeKeyPressed(NativeKeyEvent e) {

        if (ControlKey.isRecordingControlKey(e.getKeyCode())) {
            System.out.println("key pressed: " + e.getKeyCode() + " : " + e.getWhen());
            controlEvents.add(new ControlKeyEventWrapper(e.getWhen()));
            processControlKey(e);
        } else {
            ApplicationContext context = ApplicationContext.getContext();
            if (context.isRecording()) {
                //after recording is started(armed) the actual recording starts after first click or non-control key press
                if (!context.isFirstClickKeyPressMade()) {
                    context.trackFirstRecordableAction();
                }
                KeyBoardEventWrapper wrapper = new KeyBoardEventWrapper(EventType.KEYBOARD_PRESS, e);
                events.add(wrapper);
            }
        }
    }

    private void processControlKey(NativeKeyEvent e) {
        ControlKeyListener listener = ControlKeyListener.getInstance();
        ControlKey controlKey = ControlKey.valueFromInt(e.getKeyCode());
        if (controlKey == null) {
            Log.error("Unknown control key code: " + e.getKeyCode());
        } else {
            listener.processControlKeyPress(controlKey);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        //ignore control key release
        if (!ControlKey.isRecordingControlKey(e.getKeyCode()) && RecordAndPlay.isRecording) {
            KeyBoardEventWrapper wrapper = new KeyBoardEventWrapper(EventType.KEYBOARD_RELEASE, e);
            events.add(wrapper);
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    public static List<EventWrapper> getEvents() {
        return events;
    }

    public static List<EventWrapper> getControlEvents() {
        return controlEvents;
    }
}
