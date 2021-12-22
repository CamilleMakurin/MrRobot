package v1.workflowRecorder.listener;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import v1.workflowRecorder.event.EventType;
import v1.workflowRecorder.event.wrapper.EventWrapper;
import v1.workflowRecorder.event.wrapper.MouseEventWrapper;
import v1.workflowRecorder.player.RecordAndPlay;

import java.util.ArrayList;
import java.util.List;


public class MouseListener implements NativeMouseInputListener {

    public static List<EventWrapper> events = new ArrayList<>();

    public static void resetEvents() {
        events = new ArrayList<>();
    }

    public void nativeMouseClicked(NativeMouseEvent e) {
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        if (RecordAndPlay.isRecording) {
            System.out.println("mousePressed: x=" + e.getX() + ", y=" + e.getY());
            events.add(new MouseEventWrapper(EventType.MOUSE_PRESS, e));
        }
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        //System.out.println("mouseReleased: " + e.getWhen());
        if (RecordAndPlay.isRecording) {
            events.add(new MouseEventWrapper(EventType.MOUSE_RELEASE, e));
        }
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        //        System.out.println("mouse moved: " + "x: " + e.getX()+ " y: " + e.getY());
        if (RecordAndPlay.isRecording) {
            events.add(new MouseEventWrapper(EventType.MOUSE_MOVE, e));
        }
    }

    //not sure if used at all
    public void nativeMouseDragged(NativeMouseEvent e) {

    }
}
