package mkhatri.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String login;
    private String password;
    private List<Role> roles;
}
