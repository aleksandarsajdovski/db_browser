package com.db.browser.integration.services;

import com.db.browser.database.repository.ConnectionDetailsRepositoryImpl;
import com.db.browser.database.model.ConnectionDetails;
import com.db.browser.integration.mappers.ConnectionDetailsDTOMapper;
import com.db.browser.spi.ConnectionDetailsService;
import com.db.browser.spi.exceptions.DatabaseConnectionDetailsNotFoundException;
import com.db.browser.spi.model.ConnectionDetailsDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionDetailsServiceImpl implements ConnectionDetailsService {

    public static final String NO_DATABASE_CONNECTION_DETAILS_FOUND = "No database connection details found.";
    private final ConnectionDetailsRepositoryImpl connectionDetailsRepositoryImpl;
    private final ConnectionDetailsDTOMapper mapper;

    public ConnectionDetailsServiceImpl(
            ConnectionDetailsRepositoryImpl connectionDetailsRepositoryImpl,
            ConnectionDetailsDTOMapper mapper) {

        this.connectionDetailsRepositoryImpl = connectionDetailsRepositoryImpl;
        this.mapper = mapper;
    }

    @Override
    public boolean updateDatabaseConnectionDetails(long id, ConnectionDetailsDTO connectionDetailsDTO) {

        ConnectionDetails connectionDetails = mapper.convertDTOtoConnectionDetails(connectionDetailsDTO);
        boolean update = connectionDetailsRepositoryImpl.update(id, connectionDetails);
        if(!update)
            throw new DatabaseConnectionDetailsNotFoundException(NO_DATABASE_CONNECTION_DETAILS_FOUND);
        return update;
    }

    @Override
    public boolean deleteDatabaseConnectionDetails(long connectionDetailsId) {
            boolean delete = connectionDetailsRepositoryImpl.deleteById(connectionDetailsId);
        if(!delete)
            throw new DatabaseConnectionDetailsNotFoundException(NO_DATABASE_CONNECTION_DETAILS_FOUND);
        return delete;
    }

    @Override
    public boolean addDatabaseConnectionDetails(ConnectionDetailsDTO connectionDetailsDto) {
        ConnectionDetails connectionDetails =
                mapper.convertDTOtoConnectionDetails(connectionDetailsDto);
        boolean saved = connectionDetailsRepositoryImpl.save(connectionDetails);
        if(saved)
            throw new DataIntegrityViolationException("Error while saving the connection details");
        return saved;
    }

    @Override
    public ConnectionDetailsDTO getDatabaseConnectionDetails(long connectionDetailsId) {

        ConnectionDetails connectionDetails;
        try {
            connectionDetails = connectionDetailsRepositoryImpl.findById(connectionDetailsId);;
        } catch (Exception ex) {
            throw new DatabaseConnectionDetailsNotFoundException(NO_DATABASE_CONNECTION_DETAILS_FOUND, ex);
        }
        ConnectionDetailsDTO connectionDetailsDTO = mapper.convertConnectionDetailsToDTO(connectionDetails);

        return connectionDetailsDTO;
    }

    @Override
    public List<ConnectionDetailsDTO> getAllDatabaseConnectionsDetails() {

        List<ConnectionDetails> connectionDetails = connectionDetailsRepositoryImpl.findAll();
        List<ConnectionDetailsDTO> connectionDetailsDTOS =
                mapper.convertConnectionDetailsToDTOs(connectionDetails);
        return connectionDetailsDTOS;
    }
}
