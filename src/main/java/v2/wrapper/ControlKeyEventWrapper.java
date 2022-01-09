package v2.wrapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jnativehook.keyboard.NativeKeyEvent;

@Getter
@Setter
@NoArgsConstructor
public class ControlKeyEventWrapper implements EventWrapper {

    EventType eventType;
    NativeKeyEvent keyEvent;
    long when;

    public ControlKeyEventWrapper(long when) {
        this.when = when;
    }

    @Override
    public long getDelay() {
        //delay for control events is not counted and should not be used
        return 0;
    }

    @Override
    public EventType getType() {
        System.out.println("There is no eventType for ControlKeyEventWrapper");
        return null;
    }

    @Override
    public Object getNativeEvent() {
        System.out.println("There is no NativeEvent for ControlKeyEventWrapper");
        return null;
    }
}
