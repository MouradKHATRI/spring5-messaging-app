package mkhatri.service;

import mkhatri.dao.UserRepository;
import mkhatri.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private TransactionTemplate transactionTemplate;

    @Autowired
    public UserService(PlatformTransactionManager transactionManager) {
        transactionTemplate = new TransactionTemplate(transactionManager);
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
