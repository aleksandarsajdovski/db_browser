package com.db.browser.database.repository;

import com.db.browser.database.model.MetadataStructure;
import com.db.browser.database.model.TableStatistics;
import com.db.browser.database.rowmapper.DataStructureRowMapper;
import com.db.browser.database.rowmapper.TableStatisticsRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Repository for the Connection details.
 */
@Repository
public class BrowseDatabaseRepositoryImpl {

    private final JdbcTemplate jdbcTemplate;
    public static String structureColumnMetadata = "select * " +
            "FROM information_schema.columns " +
            "WHERE table_schema != 'pg_catalog' AND table_schema != 'information_schema'";

    public BrowseDatabaseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getAllSchemas() {

        List<String> query = jdbcTemplate
                .queryForList("SELECT schema_name FROM information_schema.schemata",String.class);

        return query;
    }

    public List<String> getAllTables() {
        List<String> query = jdbcTemplate
                .queryForList(
                        "SELECT table_name FROM information_schema.tables " +
                                "WHERE table_schema != 'pg_catalog' AND table_schema != 'information_schema'",
                        String.class);

        return query;
    }

    public List<String> getAllColumns() {
        List<String> query = jdbcTemplate
                .queryForList("SELECT column_name FROM information_schema.columns " +
                                "WHERE table_schema != 'pg_catalog' AND table_schema != 'information_schema'",
                        String.class);

        return query;
    }

    public List<MetadataStructure> getSimpleStructureMetadata() {

        List<MetadataStructure> query = jdbcTemplate.query(structureColumnMetadata, new DataStructureRowMapper());

        return query;
    }

    public List<Map<String, Object>> getTableStructureMetadata(String tableName) {
        String sql = "SELECT * FROM " + tableName;
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);

        return maps;
    }

    public List<TableStatistics> getTablesStatistics() {
        String sql = "SELECT ut.n_live_tup as number_rows, count(c.column_name) as number_attributes, c.table_name " +
                "FROM pg_stat_user_tables as ut INNER JOIN information_schema.columns as c ON  ut.relname = c.table_name" +
                " WHERE c.table_schema != 'pg_catalog' AND c.table_schema != 'information_schema' group by c.table_name, number_rows, ut.relname";
        List<TableStatistics> query = jdbcTemplate.query(sql, new TableStatisticsRowMapper());

        return query;
    }
}
