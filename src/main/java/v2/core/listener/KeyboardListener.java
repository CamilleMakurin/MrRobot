package v2.core.listener;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import v1.workflowRecorder.player.RecordAndPlay;
import v2.core.ApplicationContext;
import v2.core.action.producer.ControlKey;
import v2.core.exception.GenericException;
import v2.core.log.Log;
import v2.core.ui.ControlKeyListener;
import v2.core.wrapper.SpecialActionEventWrapper;
import v2.core.wrapper.EventType;
import v2.core.wrapper.EventWrapper;
import v2.core.wrapper.KeyBoardEventWrapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardListener implements NativeKeyListener {

    private static List<EventWrapper> events = new ArrayList<>();
    private static List<EventWrapper> specialActionEvents = new ArrayList<>();

   private final ControlKeyListener listener;

    @Autowired
    public KeyboardListener(ControlKeyListener listener) {
        this.listener = listener;
    }

    public static void resetEvents() {
        events = new ArrayList<>();
        specialActionEvents = new ArrayList<>();
    }

    public void nativeKeyPressed(NativeKeyEvent e) {

        if (ControlKey.isRecordingControlKey(e.getKeyCode())) {
            System.out.println("key pressed: " + e.getKeyCode() + " : " + e.getWhen());
            specialActionEvents.add(new SpecialActionEventWrapper(e.getWhen(), e));
            try {
                processControlKey(e);
            } catch (GenericException genericException) {
                genericException.printStackTrace();
            }
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

    private void processControlKey(NativeKeyEvent e) throws GenericException {
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

    public static List<EventWrapper> getSpecialActionEvents() {
        return specialActionEvents;
    }
}
