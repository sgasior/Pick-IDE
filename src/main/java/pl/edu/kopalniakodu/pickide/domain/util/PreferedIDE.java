package pl.edu.kopalniakodu.pickide.domain.util;

import java.util.Arrays;
import java.util.List;

public enum PreferedIDE {

    BEGINNER(Arrays.asList("BEG_IDE1", "BEG_IDE2")),
    MID_EXP(Arrays.asList("MID_IDE1", "MID_IDE2")),
    PRO_EXP(Arrays.asList("PRO_IDE1", "PRO_IDE2"));

    private List<String> value;

    PreferedIDE(List<String> value) {
        this.value = value;
    }

    public List<String> getReponse() {
        return value;
    }
}
