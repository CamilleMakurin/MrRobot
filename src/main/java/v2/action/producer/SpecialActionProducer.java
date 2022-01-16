package v2.action.producer;

import org.jnativehook.keyboard.NativeKeyEvent;
import v2.action.domain.Action;
import v2.action.domain.SpecialAction;
import v2.wrapper.EventWrapper;


public class SpecialActionProducer {

    public Action produceAction(EventWrapper wrapper) {


        NativeKeyEvent nativeEvent = (NativeKeyEvent) wrapper.getNativeEvent();

        return new SpecialAction(nativeEvent, ControlKey.valueFromInt(nativeEvent.getKeyCode()));
    }
}
