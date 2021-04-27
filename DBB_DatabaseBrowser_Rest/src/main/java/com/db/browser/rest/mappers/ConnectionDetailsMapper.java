package com.db.browser.rest.mappers;

import com.db.browser.rest.models.ConnectionDetailsRequest;
import com.db.browser.rest.models.ConnectionDetailsResponse;
import com.db.browser.spi.model.ConnectionDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ConnectionDetails mapper.
 */
@Component
public class ConnectionDetailsMapper {

    /**
     * Maps from {@link ConnectionDetailsDTO} to {@link ConnectionDetailsResponse}.
     *
     * @param connectionDetailsDTO the {@link ConnectionDetailsDTO}
     * @return the {@link ConnectionDetailsResponse}
     */
    public ConnectionDetailsResponse connectionDetailsDTOtoResponse(ConnectionDetailsDTO connectionDetailsDTO){
        ConnectionDetailsResponse connectionDetailsResponse = ConnectionDetailsResponse.builder()
                .id(connectionDetailsDTO.getId())
                .name(connectionDetailsDTO.getName())
                .databaseName(connectionDetailsDTO.getDatabaseName())
                .driverClassName(connectionDetailsDTO.getDriverClassName())
                .hostname(connectionDetailsDTO.getHostname())
                .password(connectionDetailsDTO.getPassword())
                .port(connectionDetailsDTO.getPort())
                .username(connectionDetailsDTO.getUsername())
                .build();
        return connectionDetailsResponse;
    }

    /**
     * Maps from {@link ConnectionDetailsDTO} to {@link ConnectionDetailsResponse}.
     *
     * @param connectionDetailsDTO the {@link ConnectionDetailsDTO}
     * @return the {@link ConnectionDetailsResponse}
     */
    public List<ConnectionDetailsResponse> connectionDetailsDTOstoResponse(List<ConnectionDetailsDTO> connectionDetailsDTO) {

        List<ConnectionDetailsResponse> connectionDetailsResponses =
                connectionDetailsDTO.stream()
                        .map(this::connectionDetailsDTOtoResponse)
                        .collect(Collectors.toList());
        return connectionDetailsResponses;
    }

    /**
     * Maps from {@link ConnectionDetailsRequest} to {@link ConnectionDetailsDTO}.
     *
     * @param connectionDetailsRequest the {@link ConnectionDetailsRequest}
     * @return the {@link ConnectionDetailsDTO}
     */

    public ConnectionDetailsDTO connectionDetailsRequestToDTO(ConnectionDetailsRequest connectionDetailsRequest) {
        ConnectionDetailsDTO connectionDetailsDTO = ConnectionDetailsDTO.builder()
                .name(connectionDetailsRequest.getName())
                .databaseName(connectionDetailsRequest.getDatabaseName())
                .driverClassName(connectionDetailsRequest.getDriverClassName())
                .hostname(connectionDetailsRequest.getHostname())
                .password(connectionDetailsRequest.getPassword())
                .port(connectionDetailsRequest.getPort())
                .username(connectionDetailsRequest.getUsername())
                .build();
        return connectionDetailsDTO;
    }
}
