package com.db.browser.spi.model.browser.statistics;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TableStatisticsDTO {

    private String tableName;
    private String numberOfRows;
    private String numberOfAttributes;
}
