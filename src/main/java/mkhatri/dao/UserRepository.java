package mkhatri.dao;

import mkhatri.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

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
        return jdbcTemplate.query("select * from user", userRowMapper());
    }

    public void update(User user) {
        jdbcTemplate.update("insert into user (id, login, password) values(?, ?, ?, ?)",
                new Object[]{user.getId(), user.getLogin(), user.getPassword()});
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from user where id=?", new Object[]{id});
    }

    RowMapper<User> userRowMapper() {
        return (rs, i) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            return user;
        };
    }
}
