package mkhatri.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserRole {
    private Long id;
    private Long userId;
    private Long roleId;
    private LocalDateTime createdAt;
}
