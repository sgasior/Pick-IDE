package pl.edu.kopalniakodu.pickide.service.ServiceImpl;

import org.springframework.stereotype.Service;
import pl.edu.kopalniakodu.pickide.domain.Role;
import pl.edu.kopalniakodu.pickide.repository.RoleRepository;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.RoleService;

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
