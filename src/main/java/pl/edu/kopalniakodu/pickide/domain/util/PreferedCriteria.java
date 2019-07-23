package pl.edu.kopalniakodu.pickide.domain.util;

import java.util.Arrays;
import java.util.List;

public enum PreferedCriteria {

    BEGINNER(Arrays.asList("BEG_CRITERIA1", "BEG_CRITERIA2")),
    MID_EXP(Arrays.asList("MID_CRITERIA1", "MID_CRITERIA2")),
    PRO_EXP(Arrays.asList("PRO_CRITERIA1", "PRO_CRITERIA2"));

    private List<String> value;

    PreferedCriteria(List<String> value) {
        this.value = value;
    }

    public List<String> getReponse() {
        return value;
    }
}
