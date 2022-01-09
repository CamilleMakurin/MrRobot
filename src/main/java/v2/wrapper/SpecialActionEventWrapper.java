package v2.wrapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jnativehook.keyboard.NativeKeyEvent;

@Getter
@Setter
@NoArgsConstructor
public class SpecialActionEventWrapper implements EventWrapper {

    EventType eventType;
    NativeKeyEvent keyEvent;
    long when;

    public SpecialActionEventWrapper(long when, NativeKeyEvent e) {
        this.when = when;
        this.eventType = EventType.SPECIAL_EVENT;
        this.keyEvent = e;
    }

    @Override
    public long getDelay() {
        //delay for control events is not counted and should not be used
        return 0;
    }

    @Override
    public EventType getType() {
        return eventType;
    }

    @Override
    public Object getNativeEvent() {
        return eventType;
    }
}
