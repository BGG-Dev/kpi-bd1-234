package app.s3rdsglue.rdswriter;

import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VotesPostsRdsWriter extends AbstractRdsWriter {

    @Autowired
    public VotesPostsRdsWriter(JdbcTemplate jdbc) {
        super(jdbc);
    }

    @Override
    void buildInsertValue(StringBuilder query, CSVRecord record) {
        query.append(record.get("vote_id"));
        query.append(',');
        query.append(record.get("post_id"));
    }

}
