package net.t00thpick1.residence.utils.parser;

public class Constant extends Equation {
    private final double value;

    public Constant(double value) {
        this.value = value;;
    }
    @Override
    public double calculate(int x, int y, int z) {
        return value;
    }

    public String toString() {
        return "(" + value + ")";
    }
}
