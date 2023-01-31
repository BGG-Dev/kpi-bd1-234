package app.s3rdsglue.rdswriter;

import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PostsRdsWriter extends AbstractRdsWriter {

    @Autowired
    public PostsRdsWriter(JdbcTemplate jdbc) {
        super(jdbc);
    }

    @Override
    void buildInsertValue(StringBuilder query, CSVRecord record) {
        query.append(record.get("id"));
        query.append(',');
        query.append('\'');
        query.append(record.get("title").replace("'", "''"));
        query.append('\'');
        query.append(',');
        query.append('\'');
        query.append(record.get("body").replace("'", "''"));
        query.append('\'');
        query.append(',');
        query.append(record.get("user_creator_id"));
        query.append(',');
        query.append("TIMESTAMP");
        query.append('\'');
        query.append(record.get("creation_timestrmpz"));
        query.append('\'');
        query.append(',');
        query.append("TIMESTAMP");
        query.append('\'');
        query.append(record.get("last_activity_timestrmpz"));
        query.append('\'');
    }

}
