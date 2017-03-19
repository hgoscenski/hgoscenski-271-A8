import java.util.*;

/**
 * Created by hgoscenski on 3/12/17.
 */

public class CompilingThing {
    private String input;
    private String workingInput;
    private String resultsTable;
    final ArrayList<Character> operators =
            new ArrayList(Arrays.asList('*','/','+','-'));
    final ArrayDeque<Character> operandsAndResults =
            new ArrayDeque<>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'));
//    private Stack<Character> resultVars;
    private Stack<Character> operandStack;
    private Stack<Character> operatorStack;


    public CompilingThing(String input) {
        this.input = input;
        resultsTable = "Results:\n" +
                       "------------------------------------------\n" +
                       "Operator | Operand 1 | Operand 2 | Result\n";
        operandStack = new Stack<Character>();
        operatorStack = new Stack<Character>();
        compile();
    }

    public void compile() {
        int counter = 0;
        int sizeOfInputString;
        workingInput = input.replaceAll(" ", "");
        try {
            checkString(workingInput);
        } catch (InputNotValid e){
            System.out.println(e);
            System.exit(2);
        }
        char[] inputArray = workingInput.toCharArray();
        sizeOfInputString = inputArray.length;

        // Blob of magic logic that roughly follows my pseudocode

        for (char currentChar : inputArray) {
            counter++;
            // For debugging purposes primarily
//            System.out.println(currentChar + ", " + operandStack.size() + ", " + operandStack.toString() + ", " + operatorStack.toString());
            // ^^
            if (sizeOfInputString == counter) {
                operandStack.push(currentChar);
                while(!operandStack.empty()) {
                    try {
                        genResults(operandStack.pop(), operandStack.pop(), operandsAndResults.pollLast(), operatorStack.pop());
//                        System.out.println("THIS IS A PROBLEM: " + currentChar + ", " + operandStack.size() + ", " + operandStack.toString() + ", " + operatorStack.toString() + "  " + sizeOfInputString);
                    }catch (EmptyStackException e){
                        //DO NOTHING
                    }
                }
            } else {
                if (isOperand(currentChar)) {
                    operandStack.push(currentChar);
                } else{
                    if (isOperator(currentChar)) {
                        if (operatorStack.empty()) {
                            operatorStack.push(currentChar);
                        } else {
                            if (evaluatePrecedence(operatorStack.peek()) < evaluatePrecedence(currentChar)) {
                                operatorStack.push(currentChar);
                            } else {
                                try {
                                    while (!(evaluatePrecedence(operatorStack.peek()) < evaluatePrecedence(currentChar))) {
//                                System.out.println("Help");
                                        genResults(operandStack.pop(), operandStack.pop(), operandsAndResults.pollLast(), operatorStack.pop());
                                    }
                                } catch (EmptyStackException e) {
                                    //DO NOTHING!
                                }
                                operatorStack.push(currentChar);
                            }
                        }
                    }
                }
            }
        }
    }

//            if (operators.contains(currentChar)) {
//                if (operatorStack.empty()) {
//                    operatorStack.add(currentChar);
//                }
//                else {
//                    currentOp = operatorStack.peek();
//                    if(!isPrecendent(currentOp, currentChar) && operandStack.size() > 1) {
////                    currentOp = operatorStack.peek();
////                    if(operandStack.size() >= 2){
////                    while (!isPrecendent(currentOp, currentChar) && operandStack.size() > 1) {
//                        operand1 = operandStack.pop();
//                        operand2 = operandStack.pop();
//                        result = resultVars.pop();
//                        genResults(operand1, operand2, result, currentOp);
//                        operatorStack.pop();
//                        operatorStack.add(currentChar);
////                    }
//                }
//                }
//            }
////                check the top of the operator stack
////                also makes sure to catch possible exception from peeking an empty stack
////                try {
////                    currentOp = operatorStack.peek();
////                } catch (EmptyStackException e) {
////                    operatorStack.add(character);
////                }
////
//////                if (operators.indexOf(currentOp) > operators.indexOf(character) + 2)
//////                    operatorStack.add(character);
//////                else {
////                    if (operandStack.size() > 1) {
////                            operand1 = operandStack.pop();
////                            operand2 = operandStack.pop();
////                            result = resultVars.pop();
////                            operandStack.add(result);
////                            genResults(operand1, operand2, result, operatorStack.pop());
////                            operatorStack.add(character);
////                    }
//////                }
////            } else {
////                operandStack.add(character);
////            }
//            else {
//                operandStack.add(currentChar);
//            }
//        }

    private void genResults(char op1, char op2, char result, char operator){
        resultsTable += "    " + operator + "    |     " + op1 + "     |     " + op2 + "     |   " + result + "\n";
        operandStack.push(result);
    }

//    From the days before I realized that the equation can only go to 'm'
//    meaning that I can use a deque and pull from the beginning and end simultaneously

//    private void genResultVarStack(){
//        char[] resArray = {'n','o','p','q','r','s','t','u','v','w','x','y','z'};
//        for (int i = 0; i<resArray.length; i++){
//            resultVars.add(resArray[i]);
//        }
//    }

    public void checkString(String input) throws InputNotValid{
        // Voodoo regex's to filter out bad things.
        // Also tells you that you did something wrong.
        // I'm not assuming you did, but if you are seeing any of these, you did.
        
        input = input.replaceAll(" ","");
        if(!input.matches("((([a-z]{1})[*/+-]{1})+[a-z]{1})")){
            if(input.matches(".*[/*+\\-]")) {
                throw new InputNotValid("The string needs to end with a variable, not an operator. \n " + input);
            } else { if(input.matches("[/*+\\-]^")){
                throw new InputNotValid("The string needs to begin with a variable, not an operator.  \n" + input);
            } else { if(input.matches(".*[/+\\-*]{2,}.*")){
                throw new InputNotValid("You cannot have two operators next to each other. \n  " + input);
            } else { if(input.matches(".*[a-z]{2,}.*")) {
                throw new InputNotValid("You cannot have two variables next to each other.  \n " + input);
            } else {
                throw new InputNotValid("Generic Error. I'm not sure what you did wrong but you well and truly borked it.\nTo attempt to troubleshoot:\n" +
                        "1. Make sure all variables are letters.\n2. Make sure there is only one variable at a time.\n" +
                        "3. Make sure the only operators you use are + / * -\n4. Rethink your life.\n" + input);
            }
            }
            }
            }
        }
    }

    public String getResults(){
        return resultsTable;
    }

    public boolean isOperand(char testchar){
        return operandsAndResults.contains(testchar);
    }
    public boolean isOperator(char testchar){
        return operators.contains(testchar);
    }
    public int evaluatePrecedence(char charThing){
        if(charThing == '/' || charThing == '*'){
            return 1;
        } else {
            // Returning a lower number to indicate lower precedence
            return 0;
        }
    }
//    private boolean isPrecendent(char stack, char current){
//        if(stack == current){
//            return false;
//        }
////        check if the current operator has precendence
//        if(operatorStack.indexOf(stack) > operatorStack.indexOf(current) + 2){
//            return true;
//        }
//        if(operatorStack.indexOf(stack) < operatorStack.indexOf(current) + 2){
//            return false;
//        }
//        return false;
//    }
//
}
