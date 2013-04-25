package fitnesse;

import fit.ColumnFixture;

public class DivisionFixture extends ColumnFixture {
    private double numerator, denominator;

    public void setNumerator(double numerator) {
        this.numerator = numerator;
    }

    public void setDenominator(double denominator) {
        this.denominator = denominator;
    }

    public double quotient() {
        return numerator/denominator;
    }
}
