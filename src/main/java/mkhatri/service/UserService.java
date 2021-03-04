package mkhatri.service;

import mkhatri.dao.UserRepository;
import mkhatri.model.User;
import mkhatri.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    private TransactionTemplate transactionTemplate;

    @Autowired
    public UserService(PlatformTransactionManager transactionManager) {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public void addUser(User user) {
        try {

        transactionTemplate.execute(transactionStatus -> {
                    userRepository.update(user);
                    user.getRoles()
                            .forEach(r -> {
                                roleService.addRole(r);
                                UserRole userRole = new UserRole();
                                userRole.setUserId(user.getId());
                                userRole.setRoleId(r.getId());
                                userRole.setCreatedAt(LocalDateTime.now());
                                userRoleService.addUserRole(userRole);
                            });
                    return 0l;
                }
        );
        } catch (Exception e) {
        }
    }

    public List<User> findAll() {
        List<User> userList = transactionTemplate.execute(transactionStatus -> {
                    System.out.println("status user transaction " + transactionStatus);
                    return userRepository.findAll();
                }
        );
        return userList;
    }

}
