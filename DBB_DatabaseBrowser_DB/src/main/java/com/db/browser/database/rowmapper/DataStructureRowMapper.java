package com.db.browser.database.rowmapper;

import com.db.browser.database.model.MetadataStructure;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataStructureRowMapper implements RowMapper<MetadataStructure> {

    @Override
    public MetadataStructure mapRow(ResultSet rs, int i) throws SQLException {
        MetadataStructure metadataStructure = MetadataStructure.builder()
                .columnName(rs.getString("column_name"))
                .dataType(rs.getString("udt_name"))
                .build();
        return metadataStructure;
    }
}
