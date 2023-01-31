package app.ee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class Extractor {

    private final Logger logger;

    private final JdbcTemplate jdbc;

    private final String queryTemplate;

    @Autowired
    public Extractor(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.logger = LoggerFactory.getLogger(Extractor.class);
        this.queryTemplate = "SELECT * FROM ";
    }

    public SqlRowSet process(String tableName) {
        String query = queryTemplate + tableName;

        logger.info("Starting processing query:\n" + query);

        SqlRowSet result = jdbc.queryForRowSet(query);

        logger.info("Finished processing query.");

        return result;
    }
}
