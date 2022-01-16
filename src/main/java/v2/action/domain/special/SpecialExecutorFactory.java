package v2.action.domain.special;

import v2.action.producer.ControlKey;

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