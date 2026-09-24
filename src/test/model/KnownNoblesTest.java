package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KnownNoblesTest {

    private KnownNobles testKnownNobles;
    private Noble richard;

    @BeforeEach
    void runBefore() {

        testKnownNobles = new KnownNobles();
        richard = new Noble("Richard", "Count", 20);

    }

    @Test
    void testConstructor() {
        assertTrue(testKnownNobles.getNobles().isEmpty());
    }

    @Test
    void testAddItem() {

        testKnownNobles.addNoble(richard);
        assertEquals(1, testKnownNobles.getNobles().size());
    }

}
