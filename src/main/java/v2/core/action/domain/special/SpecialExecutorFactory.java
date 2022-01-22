package v2.core.action.domain.special;

import org.springframework.stereotype.Component;
import v2.core.action.producer.ControlKey;

@Component
public class SpecialExecutorFactory {

    public SpecialActionExecutor getExecutor(ControlKey key) {
        switch (key) {
            case SPEC_SLEEP:
                return new SleepActionExecutor();
            case SPEC_PASTE:
                return new PasteActionExecutor();
            case SPEC_EXECUTE_FILE:
                return new ExecuteFileActionExecutor();
            default:
                return new SleepActionExecutor();
        }
    }
}
