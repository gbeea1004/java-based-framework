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
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmts.values(pstmt);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public static List selectAllObjects(String sql, RowMapper rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List objects = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                objects.add(rowMapper.mapRow(rs));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
        return objects;
    }

    public static Object selectForObject(String sql, PreparedStatementSetter pstmts, RowMapper rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmts.values(pstmt);
            rs = pstmt.executeQuery();
            rs.next();
            return rowMapper.mapRow(rs);
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }
}
