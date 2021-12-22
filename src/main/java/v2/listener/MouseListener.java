package v2.listener;

import lombok.Getter;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import v2.ApplicationContext;
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
        if (ApplicationContext.getContext().isRecording()) {
            System.out.println("recording mouse press: x=" + e.getX() + ", y=" + e.getY());
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
        if (ApplicationContext.getContext().isRecording()) {
            events.add(new MouseEventWrapper(EventType.MOUSE_MOVE, e));
        }
    }

    //not sure if used at all
    public void nativeMouseDragged(NativeMouseEvent e) {
       // System.out.println("drag: " + e.getX() +" " + e.getY() + " " +e.getButton());
        if (ApplicationContext.getContext().isRecording()) {
            events.add(new MouseEventWrapper(EventType.MOUSE_DRAG, e));
        }
    }

    public static List<EventWrapper> getEvents(){
        return events;
    }
}
