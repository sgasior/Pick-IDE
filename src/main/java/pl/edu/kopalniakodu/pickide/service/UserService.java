package pl.edu.kopalniakodu.pickide.service;

import pl.edu.kopalniakodu.pickide.domain.User;

import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickName(String nickName);
}
