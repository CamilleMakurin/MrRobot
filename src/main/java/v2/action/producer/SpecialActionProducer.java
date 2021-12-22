package v2.action.producer;

import org.jnativehook.keyboard.NativeKeyEvent;
import v2.action.ActionOrderSequenceGenerator;
import v2.action.domain.Action;
import v2.action.domain.SpecialAction;


public class SpecialActionProducer {

    public Action produceAction(NativeKeyEvent action) {
        return new SpecialAction("SPECIAL_ACTION_" + ActionOrderSequenceGenerator.getNextSpecial()
        );
    }
}
