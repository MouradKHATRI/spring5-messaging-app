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
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

@Repository
public class RoleRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Role findRoleById(Long id) {
        return jdbcTemplate.queryForObject
                ("select * from role where id=?",
                        new Object[]{id}, roleRowMapper());
    }

    public List<Role> findAll() {
        return jdbcTemplate.query("select * from role", roleRowMapper());
    }

    public void update(Role role) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
                "insert into role (name) values(?)",
                Types.VARCHAR
        );
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        PreparedStatementCreator preparedStatementCreator = preparedStatementCreatorFactory.newPreparedStatementCreator(Arrays.asList(role.getName()));
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        role.setId(keyHolder.getKey().longValue());
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from role where id=?", new Object[]{id});
    }

    RowMapper<Role> roleRowMapper() {
        return (rs, i) -> {
            Role role = new Role();
            role.setId(rs.getLong("id"));
            role.setName(rs.getString("name"));
            return role;
        };
    }
}
