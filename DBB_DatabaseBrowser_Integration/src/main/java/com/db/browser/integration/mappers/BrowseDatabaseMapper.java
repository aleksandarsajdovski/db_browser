package com.db.browser.integration.mappers;

import com.db.browser.database.model.ConnectionDetails;
import com.db.browser.database.model.TableStatistics;
import com.db.browser.spi.model.ConnectionDetailsDTO;
import com.db.browser.spi.model.browser.statistics.TableStatisticsDTO;
import org.springframework.stereotype.Component;

@Component
public class BrowseDatabaseMapper {

    /**
     * Maps from {@link TableStatistics} to {@link TableStatisticsDTO}.
     *
     * @param tableStatistics the {@link TableStatistics}
     * @return the {@link TableStatisticsDTO}
     */
    public TableStatisticsDTO convertTableStatisticsToDTO(TableStatistics tableStatistics) {
        TableStatisticsDTO tableStatisticsDTO = TableStatisticsDTO.builder()
                .tableName(tableStatistics.getTableName())
                .numberOfAttributes(tableStatistics.getNumberOfAttributes())
                .numberOfRows(tableStatistics.getNumberOfRows())
                .build();
        return tableStatisticsDTO;
    }
}
