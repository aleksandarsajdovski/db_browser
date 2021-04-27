package com.db.browser.database.repository;

import com.db.browser.database.model.ConnectionDetails;
import com.db.browser.database.rowmapper.ConnectionDetailsRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Repository for the Connection details.
 */
@Repository
public class ConnectionDetailsRepositoryImpl {

    private final JdbcTemplate jdbcTemplate;

    public ConnectionDetailsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Get all connection details found in the database.
     *
     * @return List of {@link ConnectionDetails}
     */
    public List<ConnectionDetails> findAll() {

        List<ConnectionDetails> query = jdbcTemplate.query(
                "SELECT * FROM database_connection_details",
                new ConnectionDetailsRowMapper()
        );

        return query;
    }

    /**
     * Save a new Connection details in the database.
     *
     * @param connectionDetails The {@link ConnectionDetails}.
     * @return
     */
    public boolean save(ConnectionDetails connectionDetails) {

        int saved = jdbcTemplate.update(
                "INSERT INTO database_connection_details(name, hostname, port, database_name, username, password, driverClassName)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?)",
                connectionDetails.getName(),
                connectionDetails.getHostname(),
                connectionDetails.getPort(),
                connectionDetails.getDatabaseName(),
                connectionDetails.getUsername(),
                connectionDetails.getPassword(),
                connectionDetails.getDriverClassName());
        return saved != 0;
    }

    /**
     * Get connection details found by id.
     *
     * @return the {@link ConnectionDetails}
     */
    public ConnectionDetails findById(long id) {

        return jdbcTemplate.queryForObject(
                "SELECT * FROM database_connection_details WHERE ID = ?",
                new ConnectionDetailsRowMapper(),
                id);
    }

    /**
     * Removes connection details by given id.
     *
     * @param connectionDetailsId the id.
     * @return returns true/false if the item was deleted.
     */
    public boolean deleteById(long connectionDetailsId) {

        String sql = "DELETE FROM database_connection_details WHERE id = ?";
        int updated = jdbcTemplate.update(sql, connectionDetailsId);

        return updated != 0;

    }

    /**
     * Update the given Connection details in the database.
     *
     * @param id the connection details id.
     * @param connectionDetails The {@link ConnectionDetails}.
     * @return
     */
    public boolean update(long id, ConnectionDetails connectionDetails) {

        Object[] params = {
                connectionDetails.getName(),
                connectionDetails.getHostname(),
                connectionDetails.getPort(),
                connectionDetails.getDatabaseName(),
                connectionDetails.getUsername(),
                connectionDetails.getPassword(),
                connectionDetails.getDriverClassName(),
                id
        };

        int[] types = {
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.BIGINT
        };

        String SQL = "update database_connection_details set " +
                "name = ?, " +
                "hostname = ?, " +
                "port = ?," +
                "database_name = ?," +
                "username = ?," +
                "password = ?," +
                "driverClassName = ? " +
                "where id = ?";

        int update = jdbcTemplate.update(SQL, params, types);

        return update != 0;
    }
}
