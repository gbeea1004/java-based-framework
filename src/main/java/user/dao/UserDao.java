package user.dao;

import core.jdbc.ConnectionManager;
import user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate template = new JdbcTemplate() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };

        String sql = "INSERT INTO users VALUES (?, ?, ?, ?)";
        template.execute(sql);
    }

    public void update(User user) throws SQLException {
        JdbcTemplate template = new JdbcTemplate() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }
        };

        String sql = "UPDATE users SET password = ?, name = ?, email = ? WHERE userId = ?";
        template.execute(sql);
    }

    public List<User> findAll() throws SQLException {
        SelectJdbcTemplate template = new SelectJdbcTemplate() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
            }

            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        };

        String sql = "SELECT * FROM users";
        return template.execute(sql);
    }

    public User findByUserId(String userId) throws SQLException {
        SelectJdbcTemplate template = new SelectJdbcTemplate() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }

            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        };

        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid = ?";
        List<User> users = template.execute(sql);
        return users.get(0);
    }
}

