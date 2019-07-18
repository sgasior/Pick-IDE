package pl.edu.kopalniakodu.pickide.domain.util;


public class Comparison<T> {

    private T choice1;
    private int value;

    private T choice2;


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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "Comparison{" +
                "choice1=" + choice1 +
                ", value='" + value + '\'' +
                ", choice2=" + choice2 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comparison<?> that = (Comparison<?>) o;

        if (value != that.value) return false;
        if (choice1 != null ? !choice1.equals(that.choice1) : that.choice1 != null) return false;
        return choice2 != null ? choice2.equals(that.choice2) : that.choice2 == null;
    }

    @Override
    public int hashCode() {
        int result = choice1 != null ? choice1.hashCode() : 0;
        result = 31 * result + value;
        result = 31 * result + (choice2 != null ? choice2.hashCode() : 0);
        return result;
    }
}
