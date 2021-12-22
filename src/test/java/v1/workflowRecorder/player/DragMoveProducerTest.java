package v1.workflowRecorder.player;

import v1.workflowRecorder.action.Coordinates;
import v1.workflowRecorder.action.DragMoveProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class DragMoveProducerTest {

    private DragMoveProducer dragMoveProducer;

    @BeforeEach
    void setUp() {
        dragMoveProducer = new DragMoveProducer();
    }

    @Test
    void testProduceSteps_allIncrease() {
        List<Coordinates> coordinates = dragMoveProducer.produceSteps(10, 16, 20, 27);
        System.out.println(coordinates);
    }

    @Test
    void testProduceSteps_xIncreaseYDecrease() {
        List<Coordinates> coordinates = dragMoveProducer.produceSteps(10, 16, 2, 27);
        System.out.println(coordinates);
    }

    @Test
    void testProduceSteps_xDecreaseYIncrease() {

    }

    @Test
    void testProduceSteps_xIncreaseYSame() {

    }

    @Test
    void testProduceSteps_xDecreaseYSame() {

    }
}