package pl.edu.kopalniakodu.pickide.domain.util;

public enum ProgrammingSkill {

    BEGINNER("beginner"),
    MID_EXP("medium experienced"),
    PRO("professional");

    private String value;

    private String programmingSkill;

    ProgrammingSkill(String value) {
        this.value = value;
    }

    public String getResponse() {
        return value;
    }


}
