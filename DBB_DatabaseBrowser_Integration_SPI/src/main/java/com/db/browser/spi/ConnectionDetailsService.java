package com.db.browser.spi;

import com.db.browser.spi.model.ConnectionDetailsDTO;

import java.util.List;

/**
 * Service interface for ConnectionDetails services.
 */
public interface ConnectionDetailsService {

    void updateDatabaseConnectionDetails(long connectionDetailsId, ConnectionDetailsDTO connectionDetailsDTO);

    void deleteDatabaseConnectionDetails(long connectionDetailsId);

    void addDatabaseConnectionDetails(ConnectionDetailsDTO connectionDetailsDTO);

    ConnectionDetailsDTO getDatabaseConnectionDetails(long connectionDetailsId);

    List<ConnectionDetailsDTO> getAllDatabaseConnectionsDetails();
}
