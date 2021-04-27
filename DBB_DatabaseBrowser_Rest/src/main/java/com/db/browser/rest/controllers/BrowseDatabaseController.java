package com.db.browser.rest.controllers;

import com.db.browser.spi.BrowseDatabaseService;
import com.db.browser.spi.annotations.ChangeDatasource;
import com.db.browser.spi.model.browser.preview.PreviewTableDTO;
import com.db.browser.spi.model.browser.SchemaDTO;
import com.db.browser.spi.model.browser.statistics.TableStatisticsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Browse database structure and data controller.
 */
@RestController("/database")
@Tag(name = "Browse database structure and data", description = "Browsing the database structure and data")

public class BrowseDatabaseController {

    private final BrowseDatabaseService browseDatabaseService;

    BrowseDatabaseController(BrowseDatabaseService browseDatabaseService) {
        this.browseDatabaseService = browseDatabaseService;
    }

    /**
     * Method used for listing schemas from database.
     * @param databaseId the database id used when listing the schemas.
     * @return list of all schemas.
     */
    @Operation(summary = "List schemas", description = "List all database schemas for given database id.")
    @GetMapping("{databaseId}/list-schemas")
    @ChangeDatasource
    ResponseEntity<List<String>> listSchemas(@PathVariable long databaseId) {

        List<String> allSchemas = browseDatabaseService.getSchemas();

        return ResponseEntity.ok(allSchemas);
    }

    /**
     * Method used for listing tables from database.
     * @param databaseId the database id used when listing the tables.
     * @return list of all tables.
     */
    @Operation(summary = "List tables", description = "List all database tables for given database id.")
    @GetMapping("{databaseId}/list-tables")
    @ChangeDatasource
    ResponseEntity<List<String>> listTables(@PathVariable long databaseId) {

        List<String> allSchemas = browseDatabaseService.getTables();

        return ResponseEntity.ok(allSchemas);
    }

    /**
     * Method used for listing columns from database.
     * @param databaseId the database id used when listing the columns.
     * @return list of all columns.
     */
    @Operation(summary = "List columns", description = "List all database columns for given database id.")
    @GetMapping("{databaseId}/list-columns")
    @ChangeDatasource
    ResponseEntity<List<String>> listColumns(@PathVariable long databaseId) {

        List<String> simpleStructureMetadata = browseDatabaseService.getColumns();

        return ResponseEntity.ok(simpleStructureMetadata);
    }

    /**
     * Method used for listing simple structure metadata data.
     * @param databaseId the database id used when listing the columns.
     * @return list of simple structure metadata data.
     */
    @Operation(summary = "List all simple structure metadata data.", description = "List all simple structure metadata data.")
    @GetMapping("{databaseId}/simple-structure-metadata")
    @ChangeDatasource
    ResponseEntity<List<SchemaDTO>> getSimpleStructureMetadata(@PathVariable long databaseId) {

        List<SchemaDTO> simpleStructureMetadata = browseDatabaseService.getSimpleStructureMetadata();

        return ResponseEntity.ok(simpleStructureMetadata);
    }

    /**
     * Method used for preview of data stored in the table.
     * @param databaseId the database id.
     * @param tableName the table name used for data preview.
     * @return list of simple structure metadata data.
     */
    @Operation(summary = "Preview of data stored in the table.", description = "Preview of data stored in the table.")
    @GetMapping("{databaseId}/table/{tableName}/preview")
    @ChangeDatasource
    ResponseEntity<PreviewTableDTO> getDataPreview(@PathVariable long databaseId, @PathVariable String tableName) {

        PreviewTableDTO dataPreview = browseDatabaseService.getDataPreview(tableName);

        return ResponseEntity.ok(dataPreview);
    }

    /**
     * Method used for statistics about each table.
     * @param databaseId the database id.
     * @return list of statistics about each table.
     */
    @Operation(summary = "Statistics about each table", description = "Statistics about each table")
    @GetMapping("{databaseId}/tables/statistics")
    @ChangeDatasource
    ResponseEntity<List<TableStatisticsDTO>> showTableStatistics(@PathVariable long databaseId) {

        List<TableStatisticsDTO> statisticsForTables = browseDatabaseService.getStatisticsForTables();

        return ResponseEntity.ok(statisticsForTables);
    }




}
