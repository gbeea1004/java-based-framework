package user.dao;

import exception.DataAccessException;
import user.domain.User;

import java.sql.SQLException;
import java.util.List;

public class UserDao {
    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO users VALUES (?, ?, ?, ?)";
        JdbcTemplate.updateQuery(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) throws DataAccessException {
        String sql = "UPDATE users SET password = ?, name = ?, email = ? WHERE userId = ?";
        JdbcTemplate.updateQuery(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public void delete(User user) throws DataAccessException {
        String sql = "DELETE FROM users WHERE userId = ?";
        JdbcTemplate.updateQuery(sql, user.getUserId());
    }

    public List<User> findAll() throws DataAccessException {
        String sql = "SELECT * FROM users";
        return JdbcTemplate.selectAllObjects(sql, (rs) -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")
        ));
    }

    public User findByUserId(String userId) throws DataAccessException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
        return JdbcTemplate.selectForObject(sql,
                (rs) -> new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                ),
                (pstmt) -> pstmt.setString(1, userId)
        );
    }
}

