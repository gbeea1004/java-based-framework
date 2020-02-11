package user.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementSetter {
    void values(PreparedStatement pstmt) throws SQLException;
}
