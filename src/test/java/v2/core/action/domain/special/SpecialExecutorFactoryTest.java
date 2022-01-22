package v2.core.action.domain.special;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import v2.core.action.producer.ControlKey;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class SpecialExecutorFactoryTest {

    private SpecialExecutorFactory factory;

    @BeforeEach
    void setUp() {
        factory = new SpecialExecutorFactory();
    }

    @Test
    void testGetExecutor_sleep() {
        SpecialActionExecutor executor = factory.getExecutor(ControlKey.SPEC_SLEEP);
        assertThat(executor instanceof SleepActionExecutor, is(true));
    }

    @Test
    void testGetExecutor_paste() {
        SpecialActionExecutor executor = factory.getExecutor(ControlKey.SPEC_PASTE);
        assertThat(executor instanceof PasteActionExecutor, is(true));
    }

    @Test
    void testGetExecutor_executeFile() {
        SpecialActionExecutor executor = factory.getExecutor(ControlKey.SPEC_EXECUTE_FILE);
        assertThat(executor instanceof ExecuteFileActionExecutor, is(true));
    }
}