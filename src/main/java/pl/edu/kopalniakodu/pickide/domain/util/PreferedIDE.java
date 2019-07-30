package pl.edu.kopalniakodu.pickide.domain.util;

import java.util.Arrays;
import java.util.List;

public enum PreferedIDE {

    BEGINNER(Arrays.asList("Eclipse", "NetBeans")),
    MID_EXP(Arrays.asList("IntelliJ Community")),
    PRO_EXP(Arrays.asList("IntelliJ Ultimate"));

    private List<String> value;

    PreferedIDE(List<String> value) {
        this.value = value;
    }

    public List<String> getReponse() {
        return value;
    }
}
