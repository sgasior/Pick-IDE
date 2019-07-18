package pl.edu.kopalniakodu.pickide.domain.util;


public class Comparison<T> {

    private T choice1;
    private double firstValue;

    private T choice2;
    private double secondValue;


    public Comparison(T choice1, T choice2) {
        this.choice1 = choice1;
        this.choice2 = choice2;
    }

    public T getChoice1() {
        return choice1;
    }

    public void setChoice1(T choice1) {
        this.choice1 = choice1;
    }

    public T getChoice2() {
        return choice2;
    }

    public void setChoice2(T choice2) {
        this.choice2 = choice2;
    }

    public double getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(double firstValue) {
        this.firstValue = firstValue;
        this.secondValue = 1 / firstValue;
    }

    public double getSecondValue() {
        return secondValue;
    }

    @Override
    public String toString() {
        return "Comparison{" +
                "choice1=" + choice1 +
                ", firstValue=" + firstValue +
                ", choice2=" + choice2 +
                ", secondValue=" + secondValue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comparison<?> that = (Comparison<?>) o;

        if (Double.compare(that.firstValue, firstValue) != 0) return false;
        if (Double.compare(that.secondValue, secondValue) != 0) return false;
        if (choice1 != null ? !choice1.equals(that.choice1) : that.choice1 != null) return false;
        return choice2 != null ? choice2.equals(that.choice2) : that.choice2 == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = choice1 != null ? choice1.hashCode() : 0;
        temp = Double.doubleToLongBits(firstValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (choice2 != null ? choice2.hashCode() : 0);
        temp = Double.doubleToLongBits(secondValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
