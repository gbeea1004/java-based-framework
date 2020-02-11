package user.dao;

import core.jdbc.ConnectionManager;
import exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    public static void updateQuery(String sql, PreparedStatementSetter pstmts) throws DataAccessException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmts.values(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public static void updateQuery(String sql, Object... values) throws DataAccessException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            createPreparedStatementSetter(values).values(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public static <T> List<T> selectAllObjects(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pstmts) throws DataAccessException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            List<T> objects = new ArrayList<>();
            pstmts.values(pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    objects.add(rowMapper.mapRow(rs));
                }
            }
            return objects;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public static <T> List<T> selectAllObjects(String sql, RowMapper<T> rowMapper, Object... values) throws DataAccessException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            createPreparedStatementSetter(values).values(pstmt);
            List<T> objects = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    objects.add(rowMapper.mapRow(rs));
                }
            }
            return objects;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public static <T> T selectForObject(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pstmts) throws DataAccessException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmts.values(pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return rowMapper.mapRow(rs);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private static PreparedStatementSetter createPreparedStatementSetter(Object... values) {
        return (pstmt) -> {
            int index = 1;
            for (Object value : values) {
                System.out.println(value);
                pstmt.setString(index++, (String) value);
            }
        };
    }
}
