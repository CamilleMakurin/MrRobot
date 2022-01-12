package v2.wrapper;

import lombok.Getter;
import lombok.Setter;
import org.jnativehook.keyboard.NativeKeyEvent;
import v2.ApplicationContext;

@Getter
@Setter
public class KeyBoardEventWrapper implements EventWrapper<NativeKeyEvent> {

    private EventType eventType;
    private NativeKeyEvent keyEvent;
    private long when;
    private long delay;
    private boolean isLastBeforePause = false;

    public KeyBoardEventWrapper(EventType eventType, NativeKeyEvent e) {
        this.eventType = eventType;
        this.keyEvent = e;
        this.when = keyEvent.getWhen();
        this.delay = ApplicationContext.getContext().getDelay();
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

    @Override
    public void setIsLastBeforePause(boolean isLastBeforePause) {
        this.isLastBeforePause = isLastBeforePause;
    }

    @Override
    public boolean isLastBeforePause() {
        return isLastBeforePause;
    }

}
