package pl.edu.kopalniakodu.pickide.service;

import org.springframework.stereotype.Service;
import pl.edu.kopalniakodu.pickide.domain.Role;
import pl.edu.kopalniakodu.pickide.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
