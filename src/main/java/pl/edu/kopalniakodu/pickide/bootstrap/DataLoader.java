package pl.edu.kopalniakodu.pickide.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.kopalniakodu.pickide.domain.Role;
import pl.edu.kopalniakodu.pickide.domain.User;
import pl.edu.kopalniakodu.pickide.repository.RoleRepository;
import pl.edu.kopalniakodu.pickide.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {


    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {

        LoadUsersAndRoles();
    }

    private void LoadUsersAndRoles() {

        Role user_role = new Role("ROLE_USER");
        Role admin_role = new Role("ROLE_ADMIN");

        List<Role> roleList = new ArrayList<>();
        roleList.add(user_role);
        roleList.add(admin_role);

        for (Role role : roleList) {
            roleRepository.save(role);
        }

        List<User> userList = new ArrayList<>();

        User user = new User("user@mail.com", encodePassword("user"));
        User admin = new User("admin@mail.com", encodePassword("admin"));
        userList.add(user);
        userList.add(admin);

        for (User u : userList) {
            userRepository.save(u);
        }

        user.addRole(user_role);
        userRepository.save(user);

        admin.addRoles(new HashSet<>(Arrays.asList(user_role, admin_role)));
        userRepository.save(admin);

    }

    private String encodePassword(String password) {
        return "{bcrypt}" + encoder.encode(password);
    }
}
