package com.db.browser.database.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
public class MetadataStructure {

    private String schemaName;
    private String tableName;
    private String columnName;
    private String dataType;
    private String nullable;
    private String primaryKey;
    private String charMaxLength;
}