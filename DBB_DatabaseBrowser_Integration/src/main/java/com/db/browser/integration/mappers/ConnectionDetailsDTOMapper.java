package com.db.browser.integration.mappers;

import com.db.browser.database.model.ConnectionDetails;
import com.db.browser.spi.model.ConnectionDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ConnectionDetailsDTO mapper.
 */
@Component
public class ConnectionDetailsDTOMapper {

    /**
     * Maps from {@link ConnectionDetails} to {@link ConnectionDetailsDTO}.
     *
     * @param connectionDetailsRequest the {@link ConnectionDetails}
     * @return the {@link ConnectionDetailsDTO}
     */
    public ConnectionDetailsDTO convertConnectionDetailsToDTO(ConnectionDetails connectionDetailsRequest) {
        ConnectionDetailsDTO connectionDetailsResponse = ConnectionDetailsDTO.builder()
                .id(connectionDetailsRequest.getId())
                .name(connectionDetailsRequest.getName())
                .databaseName(connectionDetailsRequest.getDatabaseName())
                .driverClassName(connectionDetailsRequest.getDriverClassName())
                .hostname(connectionDetailsRequest.getHostname())
                .password(connectionDetailsRequest.getPassword())
                .port(connectionDetailsRequest.getPort())
                .username(connectionDetailsRequest.getUsername())
                .build();
        return connectionDetailsResponse;
    }

    /**
     * Maps from {@link ConnectionDetails} to {@link ConnectionDetailsDTO}.
     *
     * @param connectionDetailsDTO the {@link ConnectionDetailsDTO}
     * @return the {@link ConnectionDetails}
     */
    public List<ConnectionDetailsDTO> convertConnectionDetailsToDTOs(List<ConnectionDetails> connectionDetailsDTO){

        List<ConnectionDetailsDTO> connectionDetailsResponses =
                connectionDetailsDTO.stream()
                        .map(this::convertConnectionDetailsToDTO)
                        .collect(Collectors.toList());
        return connectionDetailsResponses;
    }


    /**
     * Maps from {@link ConnectionDetailsDTO} to {@link ConnectionDetails}.
     *
     * @param connectionDetailsDTO the {@link ConnectionDetailsDTO}
     * @return the {@link ConnectionDetails}
     */
    public ConnectionDetails convertDTOtoConnectionDetails(ConnectionDetailsDTO connectionDetailsDTO) {
        ConnectionDetails connectionDetails = ConnectionDetails.builder()
                .id(connectionDetailsDTO.getId())
                .name(connectionDetailsDTO.getName())
                .databaseName(connectionDetailsDTO.getDatabaseName())
                .driverClassName(connectionDetailsDTO.getDriverClassName())
                .hostname(connectionDetailsDTO.getHostname())
                .password(connectionDetailsDTO.getPassword())
                .port(connectionDetailsDTO.getPort())
                .username(connectionDetailsDTO.getUsername())
                .build();
        return connectionDetails;
    }
}
