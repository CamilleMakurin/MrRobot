package v2.wrapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jnativehook.mouse.NativeMouseEvent;
import v2.ApplicationContext;
import v2.log.Log;

@Getter
@Setter
@NoArgsConstructor
public class MouseEventWrapper implements EventWrapper<NativeMouseEvent> {

    EventType eventType;
    NativeMouseEvent mouseEvent;
    long when;
    long delay;
    private boolean isLastBeforePause = false;


    public MouseEventWrapper(EventType eventType, NativeMouseEvent e) {
        this.mouseEvent = e;
        this.eventType = eventType;
        this.when = mouseEvent.getWhen();
        long d = ApplicationContext.getContext().getDelay();
        this.delay = d;
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

    @Override
    public void setIsLastBeforePause(boolean isLastBeforePause) {
        this.isLastBeforePause = isLastBeforePause;
    }

    @Override
    public boolean isLastBeforePause() {
        return isLastBeforePause;
    }
}
