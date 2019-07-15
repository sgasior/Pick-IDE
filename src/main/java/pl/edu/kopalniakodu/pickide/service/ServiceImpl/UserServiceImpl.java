package pl.edu.kopalniakodu.pickide.service.ServiceImpl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.kopalniakodu.pickide.domain.User;
import pl.edu.kopalniakodu.pickide.repository.UserRepository;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.RoleService;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {
        user.addRole(roleService.findRoleByName("ROLE_USER"));
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setConfirmPassword(encodedPassword);
        userRepository.save(user);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByNickName(String nickName) {
        return userRepository.findByNickName(nickName);
    }


}
