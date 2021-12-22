package v1.workflowRecorder.configuration;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ConfigCreationServiceTest {

    @Test
    void name() {
        Random rnd = new Random();
        for (int i = 0; i < 100; i++) {
            System.out.println(rnd.nextInt(3));
        }
    }
}