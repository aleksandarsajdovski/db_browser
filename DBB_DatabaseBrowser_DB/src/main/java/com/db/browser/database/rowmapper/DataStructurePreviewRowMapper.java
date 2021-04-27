package com.db.browser.database.rowmapper;

import com.db.browser.database.model.MetadataStructure;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataStructurePreviewRowMapper implements RowMapper<MetadataStructure> {

    @Override
    public MetadataStructure mapRow(ResultSet rs, int i) throws SQLException {
        MetadataStructure connectionDetails = MetadataStructure.builder()
                .schemaName(rs.getString("table_schema"))
                .tableName(rs.getString("table_name"))
                .columnName(rs.getString("column_name"))
                .dataType(rs.getString("udt_name"))
                .nullable(rs.getString("is_nullable"))
                .charMaxLength(rs.getString("char_max_length"))
                .build();
        return connectionDetails;
    }
}
