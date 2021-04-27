package com.db.browser.integration.services;

import com.db.browser.database.model.MetadataStructure;
import com.db.browser.database.model.TableStatistics;
import com.db.browser.database.repository.BrowseDatabaseRepositoryImpl;
import com.db.browser.integration.mappers.BrowseDatabaseMapper;
import com.db.browser.spi.BrowseDatabaseService;
import com.db.browser.spi.exceptions.DatabaseConnectionDetailsNotFoundException;
import com.db.browser.spi.model.browser.ColumnDTO;
import com.db.browser.spi.model.browser.preview.PreviewTableDTO;
import com.db.browser.spi.model.browser.SchemaDTO;
import com.db.browser.spi.model.browser.TableDTO;
import com.db.browser.spi.model.browser.preview.RowDTO;
import com.db.browser.spi.model.browser.statistics.TableStatisticsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class BrowseDatabaseServiceImpl implements BrowseDatabaseService {

    private static final Logger log = LoggerFactory.getLogger(BrowseDatabaseServiceImpl.class);
    private final BrowseDatabaseRepositoryImpl browseDatabaseRepository;
    private final BrowseDatabaseMapper mapper;

    public BrowseDatabaseServiceImpl(
            BrowseDatabaseRepositoryImpl browseDatabaseRepository,
            BrowseDatabaseMapper mapper) {
        this.browseDatabaseRepository = browseDatabaseRepository;
        this.mapper = mapper;
    }

    public List<String> getSchemas() {
        List<String> schemas = null;
        try {
            schemas = browseDatabaseRepository.getAllSchemas();
        } catch (Exception ex) {
            throw new DatabaseConnectionDetailsNotFoundException("No database connection details found.", ex);
        }
        return schemas;
    }

    @Override
    public List<String> getTables() {
        List<String> tables = null;
        try {
            tables = browseDatabaseRepository.getAllTables();
        } catch (Exception ex) {
            throw new DatabaseConnectionDetailsNotFoundException("No database connection details found.", ex);
        }
        return tables;
    }

    @Override
    public List<String> getColumns() {
        List<String> columns = null;
        try {
            columns = browseDatabaseRepository.getAllColumns();
        } catch (Exception ex) {
            throw new DatabaseConnectionDetailsNotFoundException("No database connection details found.", ex);
        }
        return columns;
    }

    @Override
    public List<SchemaDTO> getSimpleStructureMetadata() {
        List<MetadataStructure> simpleStructureMetadata = browseDatabaseRepository.getSimpleStructureMetadata();
        List<SchemaDTO> schemaDTOS = mapDBtoIntegrationDto(simpleStructureMetadata);
        return schemaDTOS;
    }

    @Override
    public List<TableStatisticsDTO> getStatisticsForTables() {

        List<TableStatistics> tablesStatistics = browseDatabaseRepository.getTablesStatistics();
        List<TableStatisticsDTO> tableStatisticsDTOS =
                tablesStatistics.stream().map(mapper::convertTableStatisticsToDTO).collect(Collectors.toList());
        return tableStatisticsDTOS;
    }

    @Override
    public PreviewTableDTO getDataPreview(String tableName) {

        List<Map<String, Object>> datapreview = browseDatabaseRepository.getTableStructureMetadata(tableName);
        PreviewTableDTO previewTableDTO = new PreviewTableDTO();

        List<RowDTO> rowValues = datapreview.stream()
                .map(row -> new RowDTO(row.values()))
                .collect(Collectors.toList());

        previewTableDTO.setRows(rowValues);
        Optional<Set<String>> columnNames = datapreview.stream().map(Map::keySet).findFirst();
        columnNames.ifPresent(previewTableDTO::setColumnName);

        return previewTableDTO;
    }

    private List<SchemaDTO> mapDBtoIntegrationDto(List<MetadataStructure> simpleStructureMetadata) {

        List<SchemaDTO> schemaDTOs = new ArrayList<>();

        for (MetadataStructure simpleStructure : simpleStructureMetadata) {

            SchemaDTO schemaDTO = addSchemaIfMissing(simpleStructure, schemaDTOs);
            List<TableDTO> tables = schemaDTO.getTables();

            TableDTO tableDTO = addTableIfMissing(simpleStructure, tables);
            String columnName = simpleStructure.getColumnName();
            ColumnDTO columnDTO = new ColumnDTO(columnName);
            tableDTO.addToColumns(columnDTO);
        }

        return schemaDTOs;
    }

    private TableDTO addTableIfMissing(MetadataStructure browseStructure, List<TableDTO> tables) {

        String tableName = browseStructure.getTableName();
        for (TableDTO table : tables) {
            if (table.getTableName().equals(tableName)) {
                return table;
            }
        }
        TableDTO table = new TableDTO(tableName);
        tables.add(table);

        return table;
    }

    private SchemaDTO addSchemaIfMissing(MetadataStructure browseStructure, List<SchemaDTO> schemaDTOs) {

        String schemaName = browseStructure.getSchemaName();
        for (SchemaDTO schemaDTO : schemaDTOs) {
            if (schemaDTO.getSchemaName().equals(schemaName)) {
                return schemaDTO;
            }
        }
        SchemaDTO schemaDTO = new SchemaDTO(schemaName);
        schemaDTOs.add(schemaDTO);
        return schemaDTO;
    }
}
