package v1.workflowRecorder.event.wrapper;

import org.jnativehook.keyboard.NativeKeyEvent;
import v1.workflowRecorder.event.EventType;

public class KeyBoardEventWrapper implements EventWrapper<NativeKeyEvent> {

    EventType eventType;
    NativeKeyEvent keyEvent;
    long when;

    public KeyBoardEventWrapper(EventType eventType, NativeKeyEvent e) {
        this.eventType = eventType;
        this.keyEvent = e;
        this.when = keyEvent.getWhen();
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
    public NativeKeyEvent getNativeEvent() {
        return keyEvent;
    }

}
