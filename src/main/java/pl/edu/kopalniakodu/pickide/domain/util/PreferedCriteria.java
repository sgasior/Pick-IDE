package pl.edu.kopalniakodu.pickide.domain.util;

import java.util.Arrays;
import java.util.List;

public enum PreferedCriteria {

    BEGINNER(Arrays.asList("Syntax autocomplete", "Clear and efficient UI")),
    MID_EXP(Arrays.asList("Support", "Built-in tools and supported frameworks")),
    PRO_EXP(Arrays.asList("Debugger quality", "Extensibility"));

    private List<String> value;

    PreferedCriteria(List<String> value) {
        this.value = value;
    }

    public List<String> getReponse() {
        return value;
    }
}
