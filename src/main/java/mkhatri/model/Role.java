package mkhatri.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Role {
    private Long id;
    private String name;
    private List<Permission> permissions;
}
