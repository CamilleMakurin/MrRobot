package v2.core.listener;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.springframework.stereotype.Component;
import v2.core.ApplicationContext;
import v2.core.log.Log;
import v2.core.wrapper.EventType;
import v2.core.wrapper.EventWrapper;
import v2.core.wrapper.MouseEventWrapper;

import java.util.ArrayList;
import java.util.List;

@Component
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
