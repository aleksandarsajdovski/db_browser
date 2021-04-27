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

@RestController("/database")
@Tag(name = "Browse database structure and data", description = "Browsing the database structure and data")

public class BrowseDatabaseController {

    private static final Logger log = LoggerFactory.getLogger(BrowseDatabaseController.class);
    private final BrowseDatabaseService browseDatabaseService;

    BrowseDatabaseController(BrowseDatabaseService browseDatabaseService) {
        this.browseDatabaseService = browseDatabaseService;
    }

    @Operation(summary = "List schemas", description = "List all database schemas for given database id.")
    @GetMapping("{databaseId}/list-schemas")
    @ChangeDatasource
    ResponseEntity<List<String>> listSchemas(@PathVariable long databaseId) {

        List<String> allSchemas = browseDatabaseService.getSchemas();

        return ResponseEntity.ok(allSchemas);
    }

    @Operation(summary = "List tables", description = "List all database tables for given database id.")
    @GetMapping("{databaseId}/list-tables")
    @ChangeDatasource
    ResponseEntity<List<String>> listTables(@PathVariable long databaseId) {

        List<String> allSchemas = browseDatabaseService.getTables();

        return ResponseEntity.ok(allSchemas);
    }

    @Operation(summary = "List columns", description = "List all database columns for given database id.")
    @GetMapping("{databaseId}/list-columns")
    @ChangeDatasource
    ResponseEntity<List<String>> listColumns(@PathVariable long databaseId) {

        List<String> simpleStructureMetadata = browseDatabaseService.getColumns();

        return ResponseEntity.ok(simpleStructureMetadata);
    }

    @Operation(summary = "List all simple structure metadata data.", description = "List all simple structure metadata data.")
    @GetMapping("{databaseId}/simple-structure-metadata")
    @ChangeDatasource
    ResponseEntity<List<SchemaDTO>> getSimpleStructureMetadata(@PathVariable long databaseId) {

        List<SchemaDTO> simpleStructureMetadata = browseDatabaseService.getSimpleStructureMetadata();

        return ResponseEntity.ok(simpleStructureMetadata);
    }

    @Operation(summary = "Preview of data store in the table.", description = "Preview of data store in the table.")
    @GetMapping("{databaseId}/table/{tableName}/preview")
    @ChangeDatasource
    ResponseEntity<PreviewTableDTO> getDataPreview(@PathVariable long databaseId, @PathVariable String tableName) {

        PreviewTableDTO dataPreview = browseDatabaseService.getDataPreview(tableName);

        return ResponseEntity.ok(dataPreview);
    }

    @Operation(summary = "Statistics about each table", description = "Statistics about each table")
    @GetMapping("{databaseId}/tables/statistics")
    @ChangeDatasource
    ResponseEntity<List<TableStatisticsDTO>> showTableStatistics(@PathVariable long databaseId) {

        List<TableStatisticsDTO> statisticsForTables = browseDatabaseService.getStatisticsForTables();

        return ResponseEntity.ok(statisticsForTables);
    }




}
