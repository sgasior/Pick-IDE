package pl.edu.kopalniakodu.pickide.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.kopalniakodu.pickide.domain.Role;
import pl.edu.kopalniakodu.pickide.domain.Survey;
import pl.edu.kopalniakodu.pickide.domain.User;
import pl.edu.kopalniakodu.pickide.domain.util.ProgrammingSkill;
import pl.edu.kopalniakodu.pickide.repository.RoleRepository;
import pl.edu.kopalniakodu.pickide.repository.SurveyRepository;
import pl.edu.kopalniakodu.pickide.repository.UserRepository;

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

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private SurveyRepository surveyRepository;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, SurveyRepository surveyRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.surveyRepository = surveyRepository;
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {

        LoadUsersAndRoles();
        LoadSurverys();
    }

    private void LoadSurverys() {
        User user = userRepository.findByEmail(USER_MAIL).get();
        User admin = userRepository.findByEmail(ADMIN_MAIL).get();

        Survey survey_1 = new Survey("AHP_BEGINNER", user, ProgrammingSkill.BEGINNER);
        Survey survey_2 = new Survey("AHP_MID", user, ProgrammingSkill.MID_EXP);
        Survey survey_3 = new Survey("AHP_PRO", admin, ProgrammingSkill.PRO);

        user.addSurvey(survey_1);
        survey_2.getUser().addSurvey(survey_2);
        admin.addSurvey(survey_3);

        userRepository.save(user);
        userRepository.save(admin);

//        surveyRepository.deleteById(1L);
//        userRepository.deleteById(1L);

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
