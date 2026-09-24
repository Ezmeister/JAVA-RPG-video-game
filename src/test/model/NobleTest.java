package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NobleTest {

    private Noble testNoble;

    @BeforeEach
    void runBefore() {
        testNoble = new Noble("Henry", "Baron", 100);

    }

    @Test
    void testConstructor() {
        assertEquals("Henry", testNoble.getName());
        assertEquals("Baron", testNoble.getTitle());
        assertEquals(100, testNoble.getPrestige());
    }

}
