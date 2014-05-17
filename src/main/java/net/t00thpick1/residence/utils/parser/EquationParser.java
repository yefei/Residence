package net.t00thpick1.residence.utils.parser;

import java.util.LinkedList;
import net.t00thpick1.residence.utils.parser.Variable.VariableType;

public abstract class EquationParser {
    public static Equation parse(String string) {
        LinkedList<Character> stack = new LinkedList<Character>();
        LinkedList<Equation> output = new LinkedList<Equation>();
        for (char c : string.toCharArray()) {
            if (c == ' ') {
                continue;
            }
            int prec = getPrecidence(c);
            if (prec != -1) {
                if (stack.size() > 0) {
                    while (true) {
                        int precStack = getPrecidence(stack.peekLast());
                        if (prec <= precStack) {
                            Equation b = output.pollLast();
                            Equation a = output.pollLast();
                            switch (stack.pollLast()) {
                                case '*':
                                    output.add(new MultiplicationEquation(a, b));
                                    break;
                                case '/':
                                    output.add(new DivisionEquation(a, b));
                                    break;
                                case '+':
                                    output.add(new AdditionEquation(a, b));
                                    break;
                                case '-':
                                    output.add(new SubtractionEquation(a, b));
                                    break;
                                case '^':
                                    output.add(new PowerEquation(a, b));
                                    break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                stack.add(c);
            } else if (c == '(') {
                stack.add(c);
            } else if (c == ')') {
                while (stack.peekLast() != '(') {
                    Equation b = output.pollLast();
                    Equation a = output.pollLast();
                    switch (stack.pollLast()) {
                        case '*':
                            output.add(new MultiplicationEquation(a, b));
                            break;
                        case '/':
                            output.add(new DivisionEquation(a, b));
                            break;
                        case '+':
                            output.add(new AdditionEquation(a, b));
                            break;
                        case '-':
                            output.add(new SubtractionEquation(a, b));
                            break;
                        case '^':
                            output.add(new PowerEquation(a, b));
                            break;
                    }
                }
                stack.pollLast();
            } else if (isVariable(c)) {
                output.add(new Variable(VariableType.valueOf(String.valueOf(c))));
            } else {
                output.add(new Constant(Double.valueOf(String.valueOf(c))));
            }
        }
        while (stack.peekLast() != null) {
            Equation b = output.pollLast();
            Equation a = output.pollLast();
            switch (stack.pollLast()) {
                case '*':
                    output.add(new MultiplicationEquation(a, b));
                    break;
                case '/':
                    output.add(new DivisionEquation(a, b));
                    break;
                case '+':
                    output.add(new AdditionEquation(a, b));
                    break;
                case '-':
                    output.add(new SubtractionEquation(a, b));
                    break;
                case '^':
                    output.add(new PowerEquation(a, b));
                    break;
            }
        }
        return output.pollLast();
    }

    private static boolean isVariable(char c) {
        return c == 'X' || c == 'Y' || c == 'Z';
    }

    private static int getPrecidence(char c) {
        if (c == '*' || c == '/') {
            return 3;
        }
        if (c == '+' || c == '-') {
            return 2;
        }
        if (c == '^') {
            return 4;
        }
        return -1;
    }
}
