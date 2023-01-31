package app.analyticsrunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public abstract class AbstractAnalytics {

    private final Logger logger;

    private final JdbcTemplate jdbc;

    private final String query;

    public AbstractAnalytics(JdbcTemplate jdbc, String query) {
        this.jdbc = jdbc;
        this.query = query;
        this.logger = LoggerFactory.getLogger(AbstractAnalytics.class);
    }

    public void process() {
        logger.info("Starting processing query:\n" + query);

        var result = jdbc.queryForRowSet(query);

        logger.info("Finished processing query.");

        while (result.next()) {
            showRow(result);
        }
    }

    abstract void showRow(SqlRowSet rows);

}
