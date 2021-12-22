package v1.workflowRecorder.configuration;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import v1.workflowRecorder.configuration.ConfigUtil;

import static org.junit.jupiter.api.Assertions.*;

class ConfigUtilTest {

    @Test
    void test() {
        Pair<String, String> result = ConfigUtil.readValue("someName==theValue");
        assertEquals("someName", result.getKey());
        assertEquals("theValue", result.getValue());
    }

    @Test
    void test_withSpaces() {
        Pair<String, String> result = ConfigUtil.readValue("someName == theValue");
        assertEquals("someName", result.getKey());
        assertEquals("theValue", result.getValue());
    }

    @Test
    void testGetDirectoryPath() {
        //  input "C:\\Program Files\\Mozilla Firefox\\firefox.exe"
        assertEquals("C:\\Program Files\\Mozilla Firefox", ConfigUtil.getDirectoryPath("C:\\Program Files\\Mozilla Firefox\\firefox.exe"));
    }
}