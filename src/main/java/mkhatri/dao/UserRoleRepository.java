package mkhatri.dao;

import mkhatri.model.User;
import mkhatri.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRoleRepository {
    private JdbcTemplate jdbcTemplate;

    private DataSource dataSource;

    @Autowired
    public UserRoleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
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
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("user_role")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userRole.getUserId());
        params.put("role_id", userRole.getRoleId());
        params.put("created_at", userRole.getCreatedAt());
        userRole.setId(simpleJdbcInsert.executeAndReturnKey(params).longValue());
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
