package com.db.browser.spi;

import com.db.browser.spi.model.browser.preview.PreviewTableDTO;
import com.db.browser.spi.model.browser.SchemaDTO;
import com.db.browser.spi.model.browser.statistics.TableStatisticsDTO;

import java.util.List;

/**
 * Service interface for browsing the database.
 */
public interface BrowseDatabaseService {

    List<String> getSchemas();

    List<String> getTables();

    List<String> getColumns();

    List<SchemaDTO> getSimpleStructureMetadata();

    PreviewTableDTO getDataPreview(String tableName);

    List<TableStatisticsDTO> getStatisticsForTables();
}
