package v2.action.producer;

import org.jnativehook.keyboard.NativeKeyEvent;
import v2.action.ActionOrderSequenceGenerator;
import v2.action.domain.Action;
import v2.action.domain.SpecialAction;
import v2.wrapper.EventWrapper;


public class SpecialActionProducer {

    public Action produceAction(EventWrapper action) {
        return new SpecialAction((NativeKeyEvent) action);
    }
}
