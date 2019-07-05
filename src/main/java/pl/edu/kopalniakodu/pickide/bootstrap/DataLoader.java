package pl.edu.kopalniakodu.pickide.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.kopalniakodu.pickide.domain.*;
import pl.edu.kopalniakodu.pickide.domain.enumUtil.ProgrammingSkill;
import pl.edu.kopalniakodu.pickide.repository.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private static final String USER_MAIL = "user@mail.com";
    private static final String USER_PASSWORD = "user";
    private static final String USER_NICKNAME = "UserNickName";

    private static final String ADMIN_MAIL = "admin@mail.com";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String ADMIN_NICKNAME = "AdminNickName";

    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private static final String SURVEY_NAME_BEGINNER = "AHP_BEGINNER";
    private static final String SURVEY_NAME_MID = "AHP_MID";
    private static final String SURVEY_NAME_PRO = "AHP_PRO";
    private static final String SURVEY_NAME_GUEST = "GUEST_SURVEY_BEGINNER";


    private static final String CRITERIA_NAME_SYNTAX = "SYNTAX";
    private static final String ALTERNATIVE__NAME_INTELIJ = "INTELLIJ IDEA";

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private SurveyRepository surveyRepository;
    private CriteriaRepository criteriaRepository;
    private AlternativeRepository alternativeRepository;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, SurveyRepository surveyRepository, CriteriaRepository criteriaRepository, AlternativeRepository alternativeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.surveyRepository = surveyRepository;
        this.criteriaRepository = criteriaRepository;
        this.alternativeRepository = alternativeRepository;
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {

        LoadUsersAndRoles();
        LoadSurverys();
//        LoadCriterias();
//        LoadAlternatives();
    }

    private void LoadAlternatives() {
        Survey survey_1 = surveyRepository.findBySurveyName(SURVEY_NAME_BEGINNER).get();
        Alternative alternative_1 = new Alternative(ALTERNATIVE__NAME_INTELIJ, survey_1);

        survey_1.addAlternative(alternative_1);
        surveyRepository.save(survey_1);
        alternativeRepository.deleteById(1L);
    }

    private void LoadCriterias() {
        Survey survey_1 = surveyRepository.findBySurveyName(SURVEY_NAME_BEGINNER).get();
        Criteria criteria_1 = new Criteria(CRITERIA_NAME_SYNTAX, survey_1);


        survey_1.addCriteria(criteria_1);
        surveyRepository.save(survey_1);
//        criteriaRepository.deleteById(1L);

    }

    private void LoadSurverys() {
        User user = userRepository.findByEmail(USER_MAIL).get();
        User admin = userRepository.findByEmail(ADMIN_MAIL).get();

        Survey survey_1 = new Survey(SURVEY_NAME_BEGINNER, user, ProgrammingSkill.BEGINNER);
        Survey survey_2 = new Survey(SURVEY_NAME_MID, user, ProgrammingSkill.MID_EXP);
        Survey survey_3 = new Survey(SURVEY_NAME_PRO, admin, ProgrammingSkill.PRO);
        Survey survey_4 = new Survey(SURVEY_NAME_GUEST, ProgrammingSkill.BEGINNER);

        user.addSurvey(survey_1);
        survey_2.getUser().addSurvey(survey_2);
        admin.addSurvey(survey_3);

        userRepository.save(user);
        userRepository.save(admin);

//        surveyRepository.deleteById(1L);
//        userRepository.deleteById(1L);

        surveyRepository.save(survey_4);

    }

    private void LoadUsersAndRoles() {

        Role user_role = new Role(ROLE_USER);
        Role admin_role = new Role(ROLE_ADMIN);

        List<Role> roleList = new ArrayList<>();
        roleList.add(user_role);
        roleList.add(admin_role);

        for (Role role : roleList) {
            roleRepository.save(role);
        }

        List<User> userList = new ArrayList<>();

        User user = new User(USER_MAIL, encodePassword(USER_PASSWORD));
        User admin = new User(ADMIN_MAIL, encodePassword(ADMIN_PASSWORD));
        user.setNickName(USER_NICKNAME);
        user.setConfirmPassword(user.getPassword());
        admin.setNickName(ADMIN_NICKNAME);
        admin.setConfirmPassword(admin.getPassword());
        userList.add(user);
        userList.add(admin);

        for (User u : userList) {
            userRepository.save(u);
        }

        user.addRole(user_role);
        userRepository.save(user);

        admin.addRole(admin_role);
//        admin.addRoles(new HashSet<>(Arrays.asList(user_role, admin_role)));
        userRepository.save(admin);

    }

    private String encodePassword(String password) {
        return "{bcrypt}" + encoder.encode(password);
    }
}
