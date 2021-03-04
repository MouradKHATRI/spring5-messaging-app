package mkhatri.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Permission {
    private Long id;
    private Long userRoleId;
    private String permission;
    private LocalDateTime createdAt;
}
