package mkhatri.service;

import mkhatri.dao.UserRepository;
import mkhatri.dao.UserRoleRepository;
import mkhatri.model.User;
import mkhatri.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    private TransactionTemplate transactionTemplate;

    @Autowired
    public UserRoleService(PlatformTransactionManager transactionManager) {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public void addUserRole(UserRole userRole) {
        userRoleRepository.update(userRole);
    }

    public List<UserRole> findAll() {
        List<UserRole> userList = transactionTemplate.execute(transactionStatus -> {
                    return userRoleRepository.findAll();
                }
        );
        return userList;
    }

}
