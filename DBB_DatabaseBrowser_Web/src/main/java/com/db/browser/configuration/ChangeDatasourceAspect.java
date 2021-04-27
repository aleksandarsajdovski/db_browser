package com.db.browser.configuration;

import com.db.browser.spi.ConnectionDetailsService;
import com.db.browser.spi.model.ConnectionDetailsDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * Change datasource aspect class.
 */
@Aspect
@Component
public class ChangeDatasourceAspect {

    private final JdbcTemplate template;
    private final ConnectionDetailsService connectionDetailsService;
    private final Map<String, DataSource> datasources;

    public ChangeDatasourceAspect(
            JdbcTemplate jdbcTemplate,
            ConnectionDetailsService connectionDetailsService, Map<String, DataSource> datasources) {

        this.template = jdbcTemplate;
        this.connectionDetailsService = connectionDetailsService;
        this.datasources = datasources;
    }

    /**
     * Around method on annotation {@link com.db.browser.spi.annotations.ChangeDatasource}.
     * Method used for switching datasources, the default one is set in a temporary variable while the target
     * datasource is fetched from the database and replaced in the jdbc template as current Datasource.
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.db.browser.spi.annotations.ChangeDatasource)")
    public Object changeDatasource(ProceedingJoinPoint joinPoint) throws Throwable {

        DataSource defaultDatasource = template.getDataSource();
        Object proceed = null;
        try {
            Optional<Object> argument = Arrays.stream(joinPoint.getArgs()).findFirst();
            argument.ifPresent(connectionDetailsId -> changeTargetDatasource((long) connectionDetailsId));

            proceed = joinPoint.proceed();
        } finally {
            template.setDataSource(defaultDatasource);
        }
        return proceed;
    }

    private void changeTargetDatasource(long connectionDetailsId) {

        String datasourceId = Long.toString(connectionDetailsId);
        DataSource targetDatasource = datasources.get(datasourceId);
        if(targetDatasource == null) {
            targetDatasource = createAndPutDatasource(connectionDetailsId);
        }
        template.setDataSource(targetDatasource);
    }

    private DataSource createAndPutDatasource(long connectionDetailsId) {

        ConnectionDetailsDTO connectionDetailsDTO = connectionDetailsService
                .getDatabaseConnectionDetails(connectionDetailsId);
        DataSource targetDatasource = buildDatasource(connectionDetailsDTO);
        String datasourceId = Long.toString(connectionDetailsId);
        datasources.putIfAbsent(datasourceId, targetDatasource);
        return targetDatasource;
    }

    private DataSource buildDatasource(ConnectionDetailsDTO connectionDetails) {

        DataSourceBuilder dsBuilder = DataSourceBuilder.create();

        String name = connectionDetails.getName();
        String hostname = connectionDetails.getHostname();
        String port = connectionDetails.getPort();
        String databaseName = connectionDetails.getDatabaseName();
        String url = String.format("jdbc:%s://%s:%s/%s", name, hostname, port, databaseName);
        dsBuilder.url(url);
        dsBuilder.username(connectionDetails.getUsername());
        dsBuilder.password(connectionDetails.getPassword());
        dsBuilder.driverClassName(connectionDetails.getDriverClassName());
        DataSource dataSource = dsBuilder.build();

        return dataSource;
    }
}