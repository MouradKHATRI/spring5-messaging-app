package mkhatri.dao;

import mkhatri.model.Permission;
import mkhatri.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PermissionRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PermissionRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Permission findPermissionById(Long id) {
        return jdbcTemplate.queryForObject
                ("select * from permission where id=?",
                        new Object[]{id}, permissionRowMapper());
    }

    public List<Permission> findAll() {
        return jdbcTemplate.query("select * from permission", permissionRowMapper());
    }

    public void update(Permission permission) {
        jdbcTemplate.update("insert into permission (id, user_role_id, permission, created_at) values(?, ?, ?, ?)",
                new Object[]{permission.getId(), permission.getUserRoleId(),
                        permission.getPermission(), permission.getCreatedAt()});
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from permission where id=?", new Object[]{id});
    }

    RowMapper<Permission> permissionRowMapper() {
        return (rs, i) -> {
            Permission permission = new Permission();
            permission.setId(rs.getLong("id"));
            permission.setUserRoleId(rs.getLong("user_role_id"));
            permission.setPermission(rs.getString("permission"));
            permission.setCreatedAt(LocalDateTime.of(rs.getDate("created_at").toLocalDate(),
                    rs.getTime("created_at").toLocalTime()) );
            return permission;
        };
    }
}
