package user.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class SelectJdbcTemplate {
    public List execute(String sql) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List objects = new ArrayList<>();
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            setValues(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                objects.add(mapRow(rs));
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

    public abstract void setValues(PreparedStatement pstmt) throws SQLException;
    public abstract Object mapRow(ResultSet rs) throws SQLException;
}
