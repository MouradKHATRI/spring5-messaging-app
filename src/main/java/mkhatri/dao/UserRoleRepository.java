package mkhatri.dao;

import mkhatri.model.User;
import mkhatri.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserRoleRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRoleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UserRole findUserById(Long id) {
        return jdbcTemplate.queryForObject
                ("select * from user_role where id=?",
                        new Object[]{id}, userRoleRowMapper());
    }

    public List<UserRole> findAll() {
        return jdbcTemplate.query("select * from user_role", userRoleRowMapper());
    }

    public void update(UserRole userRole) {
        jdbcTemplate.update("insert into user (id, user_id, role_id, created_at) values(?, ?, ?, ?)",
                new Object[]{userRole.getId(), userRole.getUserId(),
                        userRole.getRoleId(), userRole.getCreatedAt()});
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from user_role where id=?", new Object[]{id});
    }

    RowMapper<UserRole> userRoleRowMapper() {
        return (rs, i) -> {
            UserRole userRole = new UserRole();
            userRole.setId(rs.getLong("id"));
            userRole.setUserId(rs.getLong("user_id"));
            userRole.setRoleId(rs.getLong("role_id"));
            userRole.setCreatedAt(LocalDateTime.of(rs.getDate("created_at").toLocalDate(),
                    rs.getTime("created_at").toLocalTime()));
            return userRole;
        };
    }
}
