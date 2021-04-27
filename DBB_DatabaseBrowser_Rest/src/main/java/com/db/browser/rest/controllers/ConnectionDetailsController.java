package com.db.browser.rest.controllers;

import com.db.browser.rest.mappers.ConnectionDetailsMapper;
import com.db.browser.rest.models.ConnectionDetailsRequest;
import com.db.browser.rest.models.ConnectionDetailsResponse;
import com.db.browser.spi.ConnectionDetailsService;
import com.db.browser.spi.annotations.ChangeDatasource;
import com.db.browser.spi.model.ConnectionDetailsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Database connection details", description = "The database connection details API")
public class ConnectionDetailsController {

    private static final Logger log = LoggerFactory.getLogger(ConnectionDetailsController.class);

    private final ConnectionDetailsService connectionDetailsService;
    private final ConnectionDetailsMapper mapper;

    ConnectionDetailsController(ConnectionDetailsService connectionDetailsService, ConnectionDetailsMapper mapper) {
        this.connectionDetailsService = connectionDetailsService;
        this.mapper = mapper;
    }

    /**
     * Get all database connections saved in the database.
     * @return The list contained with {@link ConnectionDetailsRequest}.
     */
    @Operation(summary = "Get all database connection details.", description = "Get all database connection details.")
    @GetMapping(value = "/connections-details",
            produces = { "application/json; charset=utf-8" })
    @ChangeDatasource
    ResponseEntity<List<ConnectionDetailsResponse>> getAllDatabaseConnectionsDetails() {

        log.debug("GET received - return all database connections");
        List<ConnectionDetailsDTO> connectionsDetailDTOs =
                connectionDetailsService.getAllDatabaseConnectionsDetails();
        List<ConnectionDetailsResponse> connectionDetailsResponses =
                mapper.connectionDetailsDTOstoResponse(connectionsDetailDTOs);
        return ResponseEntity.ok(connectionDetailsResponses);
    }

    /**
     * Get database connections based on provided id.
     * @param id the database id.
     * @return the {@link ConnectionDetailsRequest}.
     */
    @Operation(summary = "Get database connections based on provided id.", description = "Get database connections based on provided id.")
    @GetMapping("/connection-details/{id}")
    @ChangeDatasource
    ResponseEntity<ConnectionDetailsResponse> getDatabaseConnectionDetails(@PathVariable long id) {

        log.debug("GET received - return database connections for " + id);
        ConnectionDetailsDTO databaseConnectionDetails =
                connectionDetailsService.getDatabaseConnectionDetails(id);
        ConnectionDetailsResponse connectionDetailsResponse =
                mapper.connectionDetailsDTOtoResponse(databaseConnectionDetails);

        return ResponseEntity.ok(connectionDetailsResponse);
    }

    @Operation(summary = "Save database connection details", description = "Save database connection details.")
    @PostMapping("/connection-details")
    ResponseEntity<Void> addDatabaseConnectionDetails(
            @Valid @RequestBody ConnectionDetailsRequest connectionDetailsRequest) {

        log.debug("POST received - ConnectionDetailsRequest: " + connectionDetailsRequest);
        ConnectionDetailsDTO connectionDetailsDTO =
                mapper.connectionDetailsRequestToDTO(connectionDetailsRequest);
        connectionDetailsService.addDatabaseConnectionDetails(connectionDetailsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Delete database connection details",
            description = "Delete given DatabaseConnection object from the database")
    @DeleteMapping("/connection-details/{id}")
    ResponseEntity<Void> deleteDatabaseConnectionDetails(@PathVariable long id) {

        log.debug("DELETE received - DatabaseConnectionId: " + id);
        connectionDetailsService.deleteDatabaseConnectionDetails(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Delete database connection details",
            description = "Remove given DatabaseConnection object from the database")
    @PutMapping("/connection-details/{id}")
    ResponseEntity<Void> updateDatabaseConnectionDetails(
            @PathVariable long id,
            @Valid @RequestBody ConnectionDetailsRequest connectionDetailsRequest) {

        log.debug("PUT received - ConnectionDetailsRequest: " + connectionDetailsRequest);
        ConnectionDetailsDTO connectionDetailsDTO =
                mapper.connectionDetailsRequestToDTO(connectionDetailsRequest);
        connectionDetailsService.updateDatabaseConnectionDetails(id, connectionDetailsDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
