package com.db.browser.database.rowmapper;

import com.db.browser.database.model.ConnectionDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionDetailsRowMapper implements RowMapper<ConnectionDetails> {

    @Override
    public ConnectionDetails mapRow(ResultSet rs, int i) throws SQLException {
        ConnectionDetails connectionDetails = ConnectionDetails.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .hostname(rs.getString("hostname"))
                .port(rs.getString("port"))
                .databaseName(rs.getString("database_name"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .driverClassName(rs.getString("driverClassName"))
                .build();
        return connectionDetails;
    }
}
