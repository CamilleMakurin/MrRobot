package v2.wrapper;

import lombok.Getter;
import lombok.Setter;
import org.jnativehook.keyboard.NativeKeyEvent;

@Getter
@Setter
public class ControlKeyEventWrapper implements EventWrapper {

    EventType eventType;
    NativeKeyEvent keyEvent;
    long when;

    public ControlKeyEventWrapper(long when) {
        this.when = when;
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
