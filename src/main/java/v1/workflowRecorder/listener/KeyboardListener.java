package v1.workflowRecorder.listener;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import v1.workflowRecorder.event.EventType;
import v1.workflowRecorder.event.wrapper.EventWrapper;
import v1.workflowRecorder.event.wrapper.KeyBoardEventWrapper;
import v1.workflowRecorder.player.RecordAndPlay;

import java.util.ArrayList;
import java.util.List;

public class KeyboardListener implements NativeKeyListener {

    public static List<EventWrapper> events = new ArrayList<>();

    public static void resetEvents(){
        events = new ArrayList<>();
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("key pressed: " + e.getKeyCode() + " char: " + e.getKeyChar());
//        String keyChecker =  NativeKeyEvent.getKeyText(e.getKeyCode());
//        if (keyChecker.equals("F12") && !workflowRecorder.player.RecordAndPlay.isRecording) {
//            workflowRecorder.player.TheRobot.moveMouse();
//        }
//        MouseListener.mouseState.add(new MouseCoordinates(e.getKeyCode(), 0, 4));
        if (RecordAndPlay.isRecording) {
            KeyBoardEventWrapper wrapper = new KeyBoardEventWrapper(EventType.KEYBOARD_PRESS, e);
            events.add(wrapper);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        //System.out.println("key released: " + e.getKeyCode() + " char: " + e.getKeyChar());
        /*System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        MouseListener.mouseState.add(new MouseCoordinates(((int) NativeKeyEvent.getKeyText(e.getKeyCode()).charAt(0)), 0, 4));
        System.out.println((int) NativeKeyEvent.getKeyText(e.getKeyCode()).charAt(0));*/
        if (RecordAndPlay.isRecording) {
            KeyBoardEventWrapper wrapper = new KeyBoardEventWrapper(EventType.KEYBOARD_RELEASE, e);
            events.add(wrapper);
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }
}
