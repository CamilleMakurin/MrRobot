package v2.wrapper;

import org.jnativehook.mouse.NativeMouseEvent;

public class MouseEventWrapper implements EventWrapper<NativeMouseEvent> {

    EventType eventType;
    NativeMouseEvent mouseEvent;
    long when;

    public MouseEventWrapper(EventType eventType, NativeMouseEvent e) {
        this.mouseEvent = e;
        this.eventType = eventType;
        this.when = mouseEvent.getWhen();
    }

    @Override
    public long getWhen() {
        return when;
    }

    @Override
    public void setWhen(long when) {
        this.when = when;
    }

    @Override
    public EventType getType() {
        return eventType;
    }

    @Override
    public NativeMouseEvent getNativeEvent() {
        return mouseEvent;
    }


}
