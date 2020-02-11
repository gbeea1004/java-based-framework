package user.dao;

import user.domain.User;

import java.sql.SQLException;
import java.util.List;

public class UserDao {
    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO users VALUES (?, ?, ?, ?)";
        JdbcTemplate.updateQuery(sql, pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        });
    }

    public void update(User user) throws SQLException {
        String sql = "UPDATE users SET password = ?, name = ?, email = ? WHERE userId = ?";
        JdbcTemplate.updateQuery(sql, (pstmt) -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        });
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM users";
        return JdbcTemplate.selectAllObjects(sql, (pstmt) -> {},
                (rs) -> new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
    }

    public User findByUserId(String userId) throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
        return JdbcTemplate.selectForObject(sql,
                (pstmt) -> pstmt.setString(1, userId),
                (rs) -> new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                )
        );
    }
}

