package pl.edu.kopalniakodu.pickide.service.ServiceInterface;

import java.util.List;

public interface EnumUtillService {

    List<String> preferedCriterias(String programmerExp);

    List<String> notPreferedCriterias(String programmerExp);

    List<String> preferedAlternatives(String programmerExp);

    List<String> notPreferedAlternatives(String programmerExp);

    boolean isProgrammingSkillMidOrPro(String programmerExp);
}
