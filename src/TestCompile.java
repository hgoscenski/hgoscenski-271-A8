/**
 * Created by hgoscenski on 3/12/17.
 */

/* My Psuedocode:
PRECEDENCE IS GREATER WHEN THE OPERATION HAS PRIORITY
IE * is greater than + because it holds precendence

Options for input from string:
    operand (valid/invalid)
        -check if in list of valid operand input
            -if not: (invalid)
        -if in list
            -add to operand stack
    operator
        -check if in list of valid operators
            -if not: (invalid)
        -if in list
            -check to see what the input is:
                -precedent 1 (*,/)
                -precedent 2 (+.-)
            -check to see what the top of the stack is:
                -p1
                -p2

            -compare precendence of input vs. stack
                input == stack
                    -pop stack and evaluate
                        -compare precendence pf input to current stack
                            ...
                                -push input to stack

                input > stack
                    -push input to stack and continue

                input < stack
                    -pop stack and evaluate
                        -compare precendence pf input to current stack
                            ...
                                -push input to stack

        This is how the results will look:

        Results: a + b * c - d / e + f * g
        ------------------------------------------
        Operator | Operand 1 | Operand 2 | Result
            *    |     c     |     b     |   z
            +    |     z     |     a     |   y
            /    |     e     |     d     |   x
            -    |     x     |     y     |   w
            *    |     g     |     f     |   v
            +    |     v     |     w     |   u
 */

public class TestCompile {
    public static void main(String args[]){
        String testInput0 = "a + b";
        String testInput1 = "a + b * c - d / e + f * g";
        String testInput2 = "a + bc + ";
        String testInput3 = "a ++ b";
        String testInput4 = "aa + b";
        String testInput5 = "a = b";
        CompilingThing compiler2;

        String[] testStrings = {testInput0,testInput1,testInput2,testInput3, testInput4, testInput5};

        for (String test:testStrings) {
            compiler2 = new CompilingThing(test);
            System.out.println(compiler2.getResults());
        }
    }
}
