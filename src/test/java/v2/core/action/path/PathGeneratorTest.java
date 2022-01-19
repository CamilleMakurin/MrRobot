package v2.core.action.path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PathGeneratorTest {

    private PathGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new PathGenerator();
    }

    @Test
    void testGeneratePath_movementLessThanSingleStepXY() {
        List<Coordinates> coordinates = generator.generatePath(0, 0, 2, 2);
        assertTrue(coordinates.isEmpty());
    }

    @Test
    void testGeneratePath_movementLessThanSingleStepX() {
        List<Coordinates> coordinates = generator.generatePath(0, 0, 2, 0);
        assertTrue(coordinates.isEmpty());
    }

    @Test
    void testGeneratePath_movementLessThanSingleStepY() {
        List<Coordinates> coordinates = generator.generatePath(0, 0, 0, 2);
        assertTrue(coordinates.isEmpty());
    }

    @Test
    void testGeneratePath_shouldNotGenerateSteps() {
        List<Coordinates> coordinates = generator.generatePath(0, 0, 1, 1);
        assertTrue(coordinates.isEmpty());
        coordinates = generator.generatePath(0, 0, 0, 1);
        assertTrue(coordinates.isEmpty());
        coordinates = generator.generatePath(0, 0, 1, 0);
        assertTrue(coordinates.isEmpty());
    }

    @Test
    void testGeneratePath_multipleStepsXYdifferent_1() {
        List<Coordinates> coords = generator.generatePath(0, 0, 4, 5);
        assertThat(coords.size(), is(2));
        Coordinates coordinates = coords.get(0);
        assertThat(coordinates.getX(), is(2));
        assertThat(coordinates.getY(), is(2));

        coordinates = coords.get(1);
        assertThat(coordinates.getX(), is(4));
        assertThat(coordinates.getY(), is(4));


    }

    @Test
    void testGeneratePath_multipleStepsXYdifferent_2() {
        List<Coordinates> coords = generator.generatePath(0, 0, 5, 4);
        assertThat(coords.size(), is(2));
        Coordinates coordinates = coords.get(0);
        assertThat(coordinates.getX(), is(2));
        assertThat(coordinates.getY(), is(2));

        coordinates = coords.get(1);
        assertThat(coordinates.getX(), is(4));
        assertThat(coordinates.getY(), is(4));
    }

    @Test
    void testGeneratePath_multipleStepsXYsame_3() {
        List<Coordinates> coords = generator.generatePath(0, 0, 5, 5);
        assertThat(coords.size(), is(2));
        Coordinates coordinates = coords.get(0);
        assertThat(coordinates.getX(), is(2));
        assertThat(coordinates.getY(), is(2));

        coordinates = coords.get(1);
        assertThat(coordinates.getX(), is(4));
        assertThat(coordinates.getY(), is(4));
    }

    @Test
    void testGeneratePath_multipleStepsXYdifferent_negativeMovement_1() {
        List<Coordinates> coords = generator.generatePath(4, 5, 0, 0);
        assertThat(coords.size(), is(2));
        Coordinates coordinates = coords.get(0);
        assertThat(coordinates.getX(), is(2));
        assertThat(coordinates.getY(), is(3));

        coordinates = coords.get(1);
        assertThat(coordinates.getX(), is(0));
        assertThat(coordinates.getY(), is(1));

    }

    @Test
    void testGeneratePath_multipleStepsXYdifferent_negativeMovement_2() {
        List<Coordinates> coords = generator.generatePath(5, 4, 0, 0);
        assertThat(coords.size(), is(2));
        Coordinates coordinates = coords.get(0);
        assertThat(coordinates.getX(), is(3));
        assertThat(coordinates.getY(), is(2));

        coordinates = coords.get(1);
        assertThat(coordinates.getX(), is(1));
        assertThat(coordinates.getY(), is(0));
    }

    @Test
    void testGeneratePath_multipleStepsXYsame_negativeMovement_3() {
        List<Coordinates> coords = generator.generatePath(5, 5, 0, 0);
        assertThat(coords.size(), is(2));
        Coordinates coordinates = coords.get(0);
        assertThat(coordinates.getX(), is(3));
        assertThat(coordinates.getY(), is(3));

        coordinates = coords.get(1);
        assertThat(coordinates.getX(), is(1));
        assertThat(coordinates.getY(), is(1));
    }


}