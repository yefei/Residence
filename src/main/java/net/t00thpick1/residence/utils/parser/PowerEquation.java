package net.t00thpick1.residence.utils.parser;

public class PowerEquation extends Equation {
    private final Equation left;
    private final Equation right;

    public PowerEquation(Equation left, Equation right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double calculate(int x, int y, int z) {
        return Math.pow(left.calculate(x, y, z), right.calculate(x, y, z));
    }
    
    public String toString() {
        return "(" + left.toString() + " ^ " + right.toString() + ")";
    }
}
