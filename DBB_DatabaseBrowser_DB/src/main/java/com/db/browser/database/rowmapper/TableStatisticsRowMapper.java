package com.db.browser.database.rowmapper;

import com.db.browser.database.model.TableStatistics;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableStatisticsRowMapper implements RowMapper<TableStatistics> {

    @Override
    public TableStatistics mapRow(ResultSet rs, int i) throws SQLException {

        TableStatistics connectionDetails = TableStatistics.builder()
                .tableName(rs.getString("table_name"))
                .numberOfRows(rs.getString("number_rows"))
                .numberOfAttributes(rs.getString("number_attributes"))
                .build();
        return connectionDetails;
    }
}
