package com.db.browser.spi.model.browser;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TableDTO {

    private String tableName;
    private List<ColumnDTO> columns;

    public TableDTO(String tableName) {
        this.tableName = tableName;
        this.columns = new ArrayList<>();
    }

    public void addToColumns(ColumnDTO columnName) {
        this.columns.add(columnName);
    }
}
