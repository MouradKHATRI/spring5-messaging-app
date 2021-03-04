package mkhatri.dao;

import mkhatri.model.Role;
import mkhatri.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User findUserById(Long id) {
        return jdbcTemplate.queryForObject
                ("select * from user where id=?",
                        new Object[]{id}, userRowMapper());
    }

    public List<User> findAll() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from user, user_role, role " +
                "where user.id=user_role.user_id and user_role.role_id=role.id");
        Map<Long, User> users = new HashMap<>();
        for (Map<String, Object> row : maps) {
            User user = new User();
            user.setId(((BigDecimal) row.get("id")).longValue());
            user.setLogin((String) row.get("login"));
            user.setPassword((String) row.get("password"));
            List<Role> roles = users.get(user.getId()) != null ?
                    users.get(user.getId()).getRoles() : new ArrayList<>();
            user.setRoles(roles);
            if (row.get("name") != null) {
                Role role = new Role();
                role.setId(((Integer) row.get("role_id")).longValue());
                role.setName((String) row.get("name"));
                roles.add(role);
            }
            users.put(user.getId(), user);
        }
        return users.values().stream().collect(Collectors.toList());
    }

    public void update(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
/*
        PreparedStatementCreator preparedStatementCreator1 = connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into user (login, password) values(?, ?)");
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            return ps;
        };*/

        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into user (login, password) values(?, ?)",
                Types.VARCHAR, Types.VARCHAR
        );
        PreparedStatementCreator preparedStatementCreator = pscf.newPreparedStatementCreator(Arrays.asList(user.getLogin(), user.getPassword()));
        pscf.setReturnGeneratedKeys(true);
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        user.setId(keyHolder.getKey().longValue());
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from user where id=?", new Object[]{id});
    }

    RowMapper<User> userRowMapper() {
        return (rs, i) -> {
            User user = new User();
            user.setId(rs.getLong("user.id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            List<Role> roles = new ArrayList<>();
            if (rs.getString("name") != null) {
                Role role = new Role();
                role.setId(rs.getLong("role.id"));
                role.setName(rs.getString("name"));
                roles.add(role);
            }
            user.setRoles(roles);
            return user;
        };
    }
}
