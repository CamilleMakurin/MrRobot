package v1.workflowRecorder.action.executable;

import org.junit.jupiter.api.Test;
import v1.workflowRecorder.action.executable.Util;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void testIsDrag_false_1() {
        assertFalse(Util.isDrag(10, 11, 15, 16));
    }

    @Test
    void testIsDrag_false_2() {
        assertFalse(Util.isDrag(10, 13, 15, 15));
    }

    @Test
    void testIsDrag_false_3() {
        assertFalse(Util.isDrag(10, 8, 15, 15));
    }

    @Test
    void testIsDrag_true_1() {
        assertTrue(Util.isDrag(10, 14, 15, 15));
    }
}
