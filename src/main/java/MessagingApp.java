import mkhatri.model.Role;
import mkhatri.model.User;
import mkhatri.service.PermissionService;
import mkhatri.service.RoleService;
import mkhatri.service.UserRoleService;
import mkhatri.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;

@ComponentScan(basePackages = "mkhatri")
@Configuration
@EnableTransactionManagement
public class MessagingApp {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(MessagingApp.class);
        PermissionService permissionService = applicationContext.getBean(PermissionService.class);
        UserService userService = applicationContext.getBean(UserService.class);
        RoleService roleService = applicationContext.getBean(RoleService.class);
        UserRoleService userRoleService = applicationContext.getBean(UserRoleService.class);
        //System.out.println("User list : " + userService.findAll());
        //System.out.println("Role list : " + roleService.findAll());
        //System.out.println("Permissions list : " + permissionService.findAll());

       // userService.addUser(createUser());

        System.out.println("User list : " + userService.findAll());
        //System.out.println("UsersRoles list : " + userRoleService.findAll());
    }

    private static User createUser() {
        User user = new User();
        user.setPassword("App_pass");
        user.setLogin("App_login");
        Role role = new Role();
        role.setName("tstRo");
        user.setRoles(Arrays.asList(role));
        return user;
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:sql/schema.sql")
                .addScript("classpath:sql/user-data.sql")
                .addScript("classpath:sql/role-data.sql")
                .addScript("classpath:sql/permission-data.sql")
                .addScript("classpath:sql/user-role-data.sql")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
