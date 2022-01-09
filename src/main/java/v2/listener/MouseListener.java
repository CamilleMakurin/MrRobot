package v2.listener;

import lombok.Getter;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import v2.ApplicationContext;
import v2.log.Log;
import v2.wrapper.EventType;
import v2.wrapper.EventWrapper;
import v2.wrapper.MouseEventWrapper;

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
        ApplicationContext context = ApplicationContext.getContext();
        if (context.isRecording()) {
            if (!context.isFirstClickKeyPressMade()) {
                Log.info("First click! Starting recording..");
                context.trackFirstRecordableAction();
            }
            System.out.println("recording mouse press: x=" + e.getX() + ", y=" + e.getY() + " : " + e.getWhen());
            events.add(new MouseEventWrapper(EventType.MOUSE_PRESS, e));
        }
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        //  System.out.println("mouseReleased: " + e.getWhen());
        if (ApplicationContext.getContext().isRecording()) {
            events.add(new MouseEventWrapper(EventType.MOUSE_RELEASE, e));
        }
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        //        System.out.println("mouse moved: " + "x: " + e.getX()+ " y: " + e.getY());
        ApplicationContext context = ApplicationContext.getContext();
        if (context.isRecording() && context.isFirstClickKeyPressMade()) {
            events.add(new MouseEventWrapper(EventType.MOUSE_MOVE, e));
        }
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        // System.out.println("drag: " + e.getX() +" " + e.getY() + " " +e.getButton());
        ApplicationContext context = ApplicationContext.getContext();
        if (context.isRecording() && context.isFirstClickKeyPressMade()) {
            events.add(new MouseEventWrapper(EventType.MOUSE_DRAG, e));
        }
    }

    public static List<EventWrapper> getEvents() {
        return events;
    }
}
