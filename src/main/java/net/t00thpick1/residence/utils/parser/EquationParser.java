package net.t00thpick1.residence.utils.parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class EquationParser {
    public static Equation parse(String string) {
        LinkedList<Character> stack = new LinkedList<Character>();
        LinkedList<Equation> output = new LinkedList<Equation>();
        StringBuilder tokenBuilder = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (c == ' ') {
                if (tokenBuilder.length() > 0) {
                    handleToken(stack, output, tokenBuilder.toString());
                    tokenBuilder = new StringBuilder();
                }
                continue;
            }
            if (isOperation(c)) {
                if (tokenBuilder.length() > 0) {
                    handleToken(stack, output, tokenBuilder.toString());
                    tokenBuilder = new StringBuilder();
                }
                int prec = getPrecidence(c);
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
                            break;
                        } else {
                            break;
                        }
                    }
                }
                stack.add(c);
            } else if (c == '(') {
                if (isSpecial(tokenBuilder.toString())) {
                    stack.add(tokenBuilder.charAt(0));
                    tokenBuilder = new StringBuilder();
                } else {
                    if (tokenBuilder.length() > 0) {
                        throw new IllegalStateException("Invalid token: " + tokenBuilder.toString());
                    }
                    stack.add(c);
                }
            } else if (c == ')') {
                if (tokenBuilder.length() > 0) {
                    handleToken(stack, output, tokenBuilder.toString());
                    tokenBuilder = new StringBuilder();
                }
                char last = stack.peekLast();
                while (last != '(' && last != 'c' && last != 's' && last != 't') {
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
                    last = stack.peekLast();
                }
                char b = stack.pollLast();
                if (b == '(') {
                    continue;
                } else {
                    switch (b) {
                        case 's':
                            output.add(new SineEquation(output.pollLast()));
                            break;
                        case 'c':
                            output.add(new CosineEquation(output.pollLast()));
                            break;
                        case 't':
                            output.add(new TangentEquation(output.pollLast()));
                            break;
                    }
                }
            } else {
                tokenBuilder.append(c);
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

    private static void handleToken(LinkedList<Character> stack, LinkedList<Equation> output, String token) {
        if (isSpecial(token)) {
            switch (token.charAt(0)) {
                case 's':
                    output.add(new SineEquation(output.pollLast()));
                    return;
                case 'c':
                    output.add(new CosineEquation(output.pollLast()));
                    return;
                case 't':
                    output.add(new TangentEquation(output.pollLast()));
                    return;
            }
        } else if (token.equalsIgnoreCase("pi")) {
            output.add(new Constant(Math.PI));
        } else if (isNumber(token)) {
            output.add(new Constant(Double.valueOf(token)));
        } else {
            output.add(new Variable(token));
        }
    }

    private static boolean isOperation(char c) {
        return c == '*' || c == '/' || c == '+' || c == '-' || c == '^';
    }

    private static boolean isNumber(String token) {
        return token.matches("^*[0-9\\.]+$");
    }

    private static boolean isSpecial(String token) {
        return token.equalsIgnoreCase("sin") || token.equalsIgnoreCase("cos") || token.equalsIgnoreCase("tan") || token.equalsIgnoreCase("ln") || token.equalsIgnoreCase("log");
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
        return 0;
    }

    public static void main(String[] args) {
        String test1 = "123 + 2 + (4/2)";
        Equation test = parse(test1);
        System.out.println(test.toString());
        System.out.println(test.calculate(null));
        String test2 = "1 + sin(2) + (4/2)";
        test = parse(test2);
        System.out.println(test.toString());
        System.out.println(test.calculate(null));
        Map<String, Double> vars = new HashMap<String, Double>();
        vars.put("X", 12D);
        String test3 = "1 + sin(X) + (4/2)";
        test = parse(test3);
        System.out.println(test.toString());
        System.out.println(test.calculate(vars));
    }
}
