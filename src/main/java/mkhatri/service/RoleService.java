package mkhatri.service;

import mkhatri.dao.RoleRepository;
import mkhatri.dao.UserRepository;
import mkhatri.model.Role;
import mkhatri.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

}
