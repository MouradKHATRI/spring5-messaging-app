package mkhatri.service;

import mkhatri.dao.PermissionRepository;
import mkhatri.dao.UserRepository;
import mkhatri.model.Permission;
import mkhatri.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    private TransactionTemplate transactionTemplate;

    @Autowired
    public PermissionService(PlatformTransactionManager transactionManager) {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    public List<Permission> findAll() {
        List<Permission> permissions = transactionTemplate.execute(transactionStatus -> {
                    //System.out.println("status user transaction " + transactionStatus);
                    return permissionRepository.findAll();
                }
        );
        return permissions;
    }

}
