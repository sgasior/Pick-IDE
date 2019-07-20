package pl.edu.kopalniakodu.pickide.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.kopalniakodu.pickide.domain.*;
import pl.edu.kopalniakodu.pickide.domain.util.ProgrammingSkill;
import pl.edu.kopalniakodu.pickide.repository.*;
import pl.edu.kopalniakodu.pickide.util.ahp.AhpAnalyzer;
import pl.edu.kopalniakodu.pickide.util.ahp.AhpAnalyzerImpl;

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
    private static final String CRITERIA_NAME_PLUGIN = "PLUGIN";
    private static final String ALTERNATIVE__NAME_INTELIJ = "INTELLIJ IDEA";

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private SurveyRepository surveyRepository;
    private CriteriaRepository criteriaRepository;
    private AlternativeRepository alternativeRepository;
    private AnswerRepository answerRepository;
    private AnswerCriteriaRepository answerCriteriaRepository;
    private AnswerAlternativeRepository answerAlternativeRepository;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, SurveyRepository surveyRepository, CriteriaRepository criteriaRepository, AlternativeRepository alternativeRepository, AnswerRepository answerRepository, AnswerCriteriaRepository answerCriteriaRepository, AnswerAlternativeRepository answerAlternativeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.surveyRepository = surveyRepository;
        this.criteriaRepository = criteriaRepository;
        this.alternativeRepository = alternativeRepository;
        this.answerRepository = answerRepository;
        this.answerCriteriaRepository = answerCriteriaRepository;
        this.answerAlternativeRepository = answerAlternativeRepository;
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {

        LoadUsersAndRoles();
        LoadSurverys();
//        LoadAnswers();
//        LoadCriterias();
//        LoadAnswerCriteria();
//        LoadAlternatives();
//        LoadAnswerAlternative();
//        LoadAHPTester();
    }

    private void LoadAHPTester() {
        double[][] ahpMatrix1 = new double[3][3];

        ahpMatrix1[0][0] = 1;
        ahpMatrix1[0][1] = 5;
        ahpMatrix1[0][2] = 3;

        ahpMatrix1[1][0] = 0.2;
        ahpMatrix1[1][1] = 1;
        ahpMatrix1[1][2] = 1;

        ahpMatrix1[2][0] = 1 / 3d;
        ahpMatrix1[2][1] = 1;
        ahpMatrix1[2][2] = 1;

        AhpAnalyzer ahpAnalyzer = new AhpAnalyzerImpl(ahpMatrix1);
//        printMatrix(ahpMatrix1);
//        System.out.println("C.I: " + ahpAnalyzer.getConsistencyIndex());
//        System.out.println("C.R: " + ahpAnalyzer.getConsistencyRatio());
//        System.out.println("Lambda max " + ahpAnalyzer.getLambdaMax());
//        System.out.println("Matrix rank " + ahpAnalyzer.getMatrixRank());
//        System.out.println("Random index " + ahpAnalyzer.getRandomIndex());
//        System.out.println("\n\n");
//        for (double weight : ahpAnalyzer.getWeights()) {
//            System.out.println("weight: " + weight);
//        }
//        System.out.println("\n\n");


        double[][] ahpMatrix2 = new double[2][2];

        ahpMatrix2[0][0] = 1;
        ahpMatrix2[0][1] = 1 / 3d;

        ahpMatrix2[1][0] = 3;
        ahpMatrix2[1][1] = 1;

        AhpAnalyzerImpl ahpAnalyzer2 = new AhpAnalyzerImpl(ahpMatrix2);
//        printMatrix(ahpMatrix2);
//        System.out.println("C.I: " + ahpAnalyzer2.getConsistencyIndex());
//        System.out.println("C.R: " + ahpAnalyzer2.getConsistencyRatio());
//        System.out.println("Lambda max " + ahpAnalyzer2.getLambdaMax());
//        System.out.println("Matrix rank " + ahpAnalyzer2.getMatrixRank());
//        System.out.println("Random index " + ahpAnalyzer2.getRandomIndex());
//        System.out.println("\n\n");
//        for (double weight : ahpAnalyzer2.getWeights()) {
//            System.out.println("weight: " + weight);
//        }
//        System.out.println("\n\n");


        double[][] ahpMatrix3 = new double[4][4];

        ahpMatrix3[0][0] = 1;
        ahpMatrix3[0][1] = 0.2;
        ahpMatrix3[0][2] = 1;
        ahpMatrix3[0][3] = 1 / 3d;

        ahpMatrix3[1][0] = 5;
        ahpMatrix3[1][1] = 1;
        ahpMatrix3[1][2] = 5;
        ahpMatrix3[1][3] = 9;

        ahpMatrix3[2][0] = 1;
        ahpMatrix3[2][1] = 0.2;
        ahpMatrix3[2][2] = 1;
        ahpMatrix3[2][3] = 1;

        ahpMatrix3[3][0] = 3;
        ahpMatrix3[3][1] = 1 / 9d;
        ahpMatrix3[3][2] = 1;
        ahpMatrix3[3][3] = 1;

        AhpAnalyzer ahpAnalyzer3 = new AhpAnalyzerImpl(ahpMatrix3);
//        printMatrix(ahpMatrix3);
//
//        System.out.println("C.I: " + ahpAnalyzer3.getConsistencyIndex());
//        System.out.println("C.R: " + ahpAnalyzer3.getConsistencyRatio());
//        System.out.println("Lambda max " + ahpAnalyzer3.getLambdaMax());
//        System.out.println("Matrix rank " + ahpAnalyzer3.getMatrixRank());
//        System.out.println("Random index " + ahpAnalyzer3.getRandomIndex());
//        System.out.println("Max consistency ratio " + ahpAnalyzer3.getMaxConsistencyRatio());
//        System.out.println("\n\n");
//        for (double weight : ahpAnalyzer3.getWeights()) {
//            System.out.println("weight: " + weight);
//        }
//        System.out.println("\n\n");
    }


    private void printMatrix(double[][] ahpMatrix) {
        for (int i = 0; i < ahpMatrix.length; i++) {
            for (int j = 0; j < ahpMatrix[i].length; j++) {
                System.out.print(" " + ahpMatrix[i][j]);
            }
            System.out.println();
        }
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


    private void LoadAnswers() {
        Survey survey_1 = surveyRepository.findBySurveyName(SURVEY_NAME_BEGINNER).get();
        Survey survey_2 = surveyRepository.findBySurveyName(SURVEY_NAME_MID).get();
        Answer answer_1 = new Answer();
        Answer answer_2 = new Answer();

        answer_1.setSurvey(survey_1);
        survey_2.addAnswwer(answer_2);

        answerRepository.save(answer_1);
        surveyRepository.save(survey_2);

    }

    private void LoadCriterias() {

        Criteria criteria_1 = new Criteria(CRITERIA_NAME_SYNTAX);
        criteriaRepository.save(criteria_1);

        Criteria criteria_2 = new Criteria(CRITERIA_NAME_PLUGIN);
        criteriaRepository.save(criteria_2);

        Criteria criteria_3 = new Criteria(CRITERIA_NAME_PLUGIN);
        criteriaRepository.save(criteria_3);


    }


    private void LoadAnswerCriteria() {
        AnswerCriteria answerCriteria_1 = new AnswerCriteria();
        AnswerCriteria answerCriteria_2 = new AnswerCriteria();

        Answer answer_1 = answerRepository.findById(1L).get();
        Answer answer_2 = answerRepository.findById(2L).get();
        Criteria criteria_1 = criteriaRepository.findById(1L).get();

        answerCriteria_1.setAnswer(answer_1);
        answerCriteria_1.setCriteria(criteria_1);

        answerCriteria_2.setAnswer(answer_2);
        answerCriteria_2.setCriteria(criteria_1);

        answerCriteriaRepository.save(answerCriteria_1);
        answerCriteriaRepository.save(answerCriteria_2);

    }


    private void LoadAlternatives() {
        Alternative alternative_1 = new Alternative(ALTERNATIVE__NAME_INTELIJ);
        alternativeRepository.save(alternative_1);

    }


    private void LoadAnswerAlternative() {
        AnswerAlternative answerAlternative = new AnswerAlternative();

        Answer answer_2 = answerRepository.findById(2L).get();
        Alternative alternative_2 = alternativeRepository.findById(2L).get();

        answerAlternative.setAlternative(alternative_2);
        answerAlternative.setAnswer(answer_2);

        answerAlternativeRepository.save(answerAlternative);
    }


    private String encodePassword(String password) {
        return "{bcrypt}" + encoder.encode(password);
    }
}
