package pl.edu.kopalniakodu.pickide.domain.util;

public enum ProgrammingSkill {

    BEGINNER("beginner"),
    MID_EXP("medium experienced"),
    PRO("professional");

    private String programmingSkill;

    private ProgrammingSkill(String programmingSkill) {
        this.programmingSkill = programmingSkill;
    }
}
