package net.t00thpick1.residence.utils.parser;

public class Variable extends Equation {
    private final VariableType variableType;

    public Variable(VariableType valueOf) {
        this.variableType = valueOf;
    }

    @Override
    public double calculate(int x, int y, int z) {
        switch (variableType) {
            case X:
                return x;
            case Y:
                return y;
            case Z:
                return z;
        }
        return 0;
    }

    public String toString() {
        return variableType.name();
    }

    public enum VariableType {
        X,
        Y,
        Z;
    }
}
