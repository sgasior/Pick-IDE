package pl.edu.kopalniakodu.pickide.domain.util;

import pl.edu.kopalniakodu.pickide.domain.Criteria;

import java.util.ArrayList;
import java.util.List;

public class RatingProvider {

    public List<Rating> getRatings() {
        return ratings;
    }

    private List<Rating> ratings = new ArrayList<>();


    public RatingProvider() {

        ratings.add(new Rating("Syntax autocomplete", "Eclipse", 49.0));
        ratings.add(new Rating("Syntax autocomplete", "NetBeans", 58.0));
        ratings.add(new Rating("Syntax autocomplete", "IntelliJ Community", 82.0));
        ratings.add(new Rating("Syntax autocomplete", "IntelliJ Ultimate", 89.0));


        ratings.add(new Rating("Clear and efficient UI", "Eclipse", 45.0));
        ratings.add(new Rating("Clear and efficient UI", "NetBeans", 63.0));
        ratings.add(new Rating("Clear and efficient UI", "IntelliJ Community", 81.0));
        ratings.add(new Rating("Clear and efficient UI", "IntelliJ Ultimate", 82.0));


        ratings.add(new Rating("Support", "Eclipse", 54.0));
        ratings.add(new Rating("Support", "NetBeans", 44.0));
        ratings.add(new Rating("Support", "IntelliJ Community", 78.0));
        ratings.add(new Rating("Support", "IntelliJ Ultimate", 86.0));

        ratings.add(new Rating("Built-in tools and supported frameworks", "Eclipse", 57.0));
        ratings.add(new Rating("Built-in tools and supported frameworks", "NetBeans", 55.0));
        ratings.add(new Rating("Built-in tools and supported frameworks", "IntelliJ Community", 67.0));
        ratings.add(new Rating("Built-in tools and supported frameworks", "IntelliJ Ultimate", 88.0));


        ratings.add(new Rating("Debugger quality", "Eclipse", 57.0));
        ratings.add(new Rating("Debugger quality", "NetBeans", 62.0));
        ratings.add(new Rating("Debugger quality", "IntelliJ Community", 76.0));
        ratings.add(new Rating("Debugger quality", "IntelliJ Ultimate", 82.0));

        ratings.add(new Rating("Extensibility", "Eclipse", 68.0));
        ratings.add(new Rating("Extensibility", "NetBeans", 54.0));
        ratings.add(new Rating("Extensibility", "IntelliJ Community", 70.0));
        ratings.add(new Rating("Extensibility", "IntelliJ Ultimate", 88.0));

    }


    public static List<Rating> findRatingByCriteria(Criteria criteria, List<Rating> ratings) {

        List<Rating> result = new ArrayList<>();

        for (Rating rating : ratings) {
            if (rating.getPreferedCriteria().equals(criteria.getCriteriaName())) {
                result.add(rating);
            }
        }
        return result;

    }

}
