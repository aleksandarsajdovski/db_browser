package com.db.browser.database.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class TableStatistics {

    private String tableName;
    private String numberOfRows;
    private String numberOfAttributes;
}