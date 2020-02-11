package user.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    public static void updateQuery(String sql, PreparedStatementSetter pstmts) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmts.values(pstmt);
            pstmt.executeUpdate();
        }
    }

    public static <T> List<T> selectAllObjects(String sql, PreparedStatementSetter pstmts, RowMapper rowMapper) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            List<T> objects = new ArrayList<>();
            pstmts.values(pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    objects.add((T) rowMapper.mapRow(rs));
                }
            }
            return objects;
        }
    }

    public static <T> T selectForObject(String sql, PreparedStatementSetter pstmts, RowMapper rowMapper) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmts.values(pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                return (T) rowMapper.mapRow(rs);
            }
        }
    }
}
