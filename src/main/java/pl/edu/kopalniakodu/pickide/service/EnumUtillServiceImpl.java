package pl.edu.kopalniakodu.pickide.service;

import org.springframework.stereotype.Service;
import pl.edu.kopalniakodu.pickide.domain.enumUtil.PreferedCriteria;
import pl.edu.kopalniakodu.pickide.domain.enumUtil.PreferedIDE;
import pl.edu.kopalniakodu.pickide.domain.enumUtil.ProgrammingSkill;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnumUtillServiceImpl implements EnumUtillService {


    @Override
    public List<String> preferedCriterias(String programmerExp) {

        ProgrammingSkill programmingSkill = getProgrammingSkill(programmerExp);

        List<String> preferedCriterias;
        if (programmingSkill.getResponse().equals(ProgrammingSkill.BEGINNER.getResponse())) {
            preferedCriterias = PreferedCriteria.BEGINNER.getReponse();
        } else if ((programmingSkill.getResponse().equals(ProgrammingSkill.MID_EXP.getResponse()))) {
            preferedCriterias = PreferedCriteria.MID_EXP.getReponse();
        } else {
            preferedCriterias = PreferedCriteria.PRO_EXP.getReponse();
        }

        return preferedCriterias;

    }

    @Override
    public List<String> notPreferedCriterias(String programmerExp) {

        ProgrammingSkill programmingSkill = getProgrammingSkill(programmerExp);
        List<String> notPreferedCriterias = new ArrayList<>();
        if (programmingSkill.getResponse().equals(ProgrammingSkill.BEGINNER.getResponse())) {
            List<String> midExpCriterias = PreferedCriteria.MID_EXP.getReponse();
            List<String> proExpCriterias = PreferedCriteria.PRO_EXP.getReponse();
            notPreferedCriterias.addAll(midExpCriterias);
            notPreferedCriterias.addAll(proExpCriterias);

        } else if ((programmingSkill.getResponse().equals(ProgrammingSkill.MID_EXP.getResponse()))) {
            List<String> midExpCriterias = PreferedCriteria.BEGINNER.getReponse();
            List<String> proExpCriterias = PreferedCriteria.PRO_EXP.getReponse();
            notPreferedCriterias.addAll(midExpCriterias);
            notPreferedCriterias.addAll(proExpCriterias);
        } else {
            List<String> midExpCriterias = PreferedCriteria.BEGINNER.getReponse();
            List<String> proExpCriterias = PreferedCriteria.MID_EXP.getReponse();
            notPreferedCriterias.addAll(midExpCriterias);
            notPreferedCriterias.addAll(proExpCriterias);
        }
        return notPreferedCriterias;
    }

    @Override
    public List<String> preferedAlternatives(String programmerExp) {
        ProgrammingSkill programmingSkill = getProgrammingSkill(programmerExp);

        List<String> preferedAlternatives;
        if (programmingSkill.getResponse().equals(ProgrammingSkill.BEGINNER.getResponse())) {
            preferedAlternatives = PreferedIDE.BEGINNER.getReponse();
        } else if ((programmingSkill.getResponse().equals(ProgrammingSkill.MID_EXP.getResponse()))) {
            preferedAlternatives = PreferedIDE.MID_EXP.getReponse();
        } else {
            preferedAlternatives = PreferedIDE.PRO_EXP.getReponse();
        }

        return preferedAlternatives;
    }

    @Override
    public List<String> notPreferedAlternatives(String programmerExp) {
        ProgrammingSkill programmingSkill = getProgrammingSkill(programmerExp);
        List<String> notPreferedAlternatives = new ArrayList<>();
        if (programmingSkill.getResponse().equals(ProgrammingSkill.BEGINNER.getResponse())) {
            List<String> midExpAlternatives = PreferedIDE.MID_EXP.getReponse();
            List<String> proExpAlternatives = PreferedIDE.PRO_EXP.getReponse();
            notPreferedAlternatives.addAll(midExpAlternatives);
            notPreferedAlternatives.addAll(proExpAlternatives);

        } else if ((programmingSkill.getResponse().equals(ProgrammingSkill.MID_EXP.getResponse()))) {
            List<String> midExpAlternatives = PreferedIDE.BEGINNER.getReponse();
            List<String> proExpAlternatives = PreferedIDE.PRO_EXP.getReponse();
            notPreferedAlternatives.addAll(midExpAlternatives);
            notPreferedAlternatives.addAll(proExpAlternatives);
        } else {
            List<String> midExpAlternatives = PreferedIDE.BEGINNER.getReponse();
            List<String> proExpAlternatives = PreferedIDE.MID_EXP.getReponse();
            notPreferedAlternatives.addAll(midExpAlternatives);
            notPreferedAlternatives.addAll(proExpAlternatives);
        }
        return notPreferedAlternatives;
    }

    private ProgrammingSkill getProgrammingSkill(String programmerExp) {
        ProgrammingSkill programmingSkill;
        if (programmerExp.equals("newbieExp")) {
            programmingSkill = ProgrammingSkill.BEGINNER;
        } else if (programmerExp.equals("midExp")) {
            programmingSkill = ProgrammingSkill.MID_EXP;
        } else {
            programmingSkill = ProgrammingSkill.PRO;
        }
        return programmingSkill;
    }


}
