import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by hgoscenski on 3/15/17.
 */
class CompilingThingTest {
    @Test
    void getResults() {
        CompilingThing c = new CompilingThing("a + b");
        assertEquals("\nResults: a + b\n" +
                "------------------------------------------\n" +
                "Operator | Operand 1 | Operand 2 | Result\n " +
                "   +    |     b     |     a     |   z\n", c.getResults());
    }

    @Test
    void isOperand() {
        CompilingThing c = new CompilingThing("a + b");
        assertTrue(c.isOperand('y'));
        assertFalse(c.isOperand('+'));
        assertTrue(c.isOperand('c'));
        assertFalse(c.isOperand(' '));
    }

    @Test
    void isOperator() {
        CompilingThing c = new CompilingThing("a + b");
        assertFalse(c.isOperator('b'));
        assertTrue(c.isOperator('+'));
        assertFalse(c.isOperator('m'));
        assertTrue(c.isOperator('/'));
    }

    @Test
    void evaluatePrecedence() {
        CompilingThing c = new CompilingThing("a + b");
        assertEquals(1, c.evaluatePrecedence('/'));
        assertEquals(0, c.evaluatePrecedence('+'));
    }
}