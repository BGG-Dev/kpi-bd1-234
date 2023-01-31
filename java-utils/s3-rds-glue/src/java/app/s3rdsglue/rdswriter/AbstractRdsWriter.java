package app.s3rdsglue.rdswriter;

import app.s3rdsglue.S3CsvReader;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public abstract class AbstractRdsWriter {

    private final JdbcTemplate jdbc;

    private final Logger logger;

    AbstractRdsWriter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.logger = LoggerFactory.getLogger(S3CsvReader.class);
    }

    public void insertIntoRds(String tableName, List<CSVRecord> records) {
        logger.info("Start rds insertion into " + tableName + " table");

        String query = buildInsertQuery(tableName, records);

        logger.info("Insert query: " + query);

        jdbc.update(query);

        logger.info("End rds insertion into " + tableName + " table");
    }

    private String buildInsertQuery(String tableName, List<CSVRecord> records) {
        StringBuilder query = new StringBuilder();

        query.append("INSERT INTO ");
        query.append(tableName);

        query.append(" VALUES ");
        for (var record : records) {
            query.append('(');
            buildInsertValue(query, record);
            query.append("),");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(";");

        return query.toString();
    }

    abstract void buildInsertValue(StringBuilder query, CSVRecord record);
}
