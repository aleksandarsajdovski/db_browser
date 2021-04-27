package com.db.browser.spi.model.browser;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SchemaDTO {

    private String schemaName;
    private List<TableDTO> tables;

    public SchemaDTO(String schemaName) {
        this.schemaName = schemaName;
        this.tables = new ArrayList<>();
    }
}
