package pl.edu.kopalniakodu.pickide.domain.util;

public class Rating {

    private String preferedCriteria;

    private String preferedAlternative;

    private Double points;



    public Rating() {
    }

    public Rating(String preferedCriteria, String preferedAlternative, Double points) {
        this.preferedCriteria = preferedCriteria;
        this.preferedAlternative = preferedAlternative;
        this.points = points;
    }

    public String getPreferedCriteria() {
        return preferedCriteria;
    }

    public void setPreferedCriteria(String preferedCriteria) {
        this.preferedCriteria = preferedCriteria;
    }

    public String getPreferedAlternative() {
        return preferedAlternative;
    }

    public void setPreferedAlternative(String preferedAlternative) {
        this.preferedAlternative = preferedAlternative;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }


    @Override
    public String toString() {
        return "Rating{" +
                "preferedCriteria='" + preferedCriteria + '\'' +
                ", preferedAlternative='" + preferedAlternative + '\'' +
                ", points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        if (preferedCriteria != null ? !preferedCriteria.equals(rating.preferedCriteria) : rating.preferedCriteria != null)
            return false;
        if (preferedAlternative != null ? !preferedAlternative.equals(rating.preferedAlternative) : rating.preferedAlternative != null)
            return false;
        return points != null ? points.equals(rating.points) : rating.points == null;
    }

    @Override
    public int hashCode() {
        int result = preferedCriteria != null ? preferedCriteria.hashCode() : 0;
        result = 31 * result + (preferedAlternative != null ? preferedAlternative.hashCode() : 0);
        result = 31 * result + (points != null ? points.hashCode() : 0);
        return result;
    }
}