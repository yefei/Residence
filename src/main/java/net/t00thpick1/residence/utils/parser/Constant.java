package net.t00thpick1.residence.utils.parser;

import java.util.Map;

public class Constant extends Equation {
    private final double value;

    public Constant(double value) {
        this.value = value;;
    }

    @Override
    public double calculate(Map<String, Double> variables) {
        return value;
    }

    public String toString() {
        return "(" + value + ")";
    }
}
