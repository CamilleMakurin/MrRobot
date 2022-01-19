package v2.core.action.producer;

import org.jnativehook.keyboard.NativeKeyEvent;
import v2.core.action.domain.Action;
import v2.core.action.domain.SpecialAction;
import v2.core.wrapper.EventWrapper;


public class SpecialActionProducer {

    public Action produceAction(EventWrapper wrapper) {


        NativeKeyEvent nativeEvent = (NativeKeyEvent) wrapper.getNativeEvent();

        return new SpecialAction(nativeEvent, ControlKey.valueFromInt(nativeEvent.getKeyCode()));
    }
}
