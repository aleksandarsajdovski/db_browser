package com.db.browser.spi;

import com.db.browser.spi.model.ConnectionDetailsDTO;

import java.util.List;

/**
 * Service interface for ConnectionDetails services.
 */
public interface ConnectionDetailsService {

    boolean updateDatabaseConnectionDetails(long connectionDetailsId, ConnectionDetailsDTO connectionDetailsDTO);

    boolean deleteDatabaseConnectionDetails(long connectionDetailsId);

    boolean addDatabaseConnectionDetails(ConnectionDetailsDTO connectionDetailsDTO);

    ConnectionDetailsDTO getDatabaseConnectionDetails(long connectionDetailsId);

    List<ConnectionDetailsDTO> getAllDatabaseConnectionsDetails();
}
