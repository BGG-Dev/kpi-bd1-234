package app.s3rdsglue.rdswriter;

import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VotesRdsWriter extends AbstractRdsWriter {

    @Autowired
    public VotesRdsWriter(JdbcTemplate jdbc) {
        super(jdbc);
    }

    @Override
    void buildInsertValue(StringBuilder query, CSVRecord record) {
        query.append(record.get("id"));
        query.append(',');
        query.append(record.get("user_voter_id"));
        query.append(',');
        query.append(record.get("vote_type_id"));
        query.append(',');
        query.append("TIMESTAMP");
        query.append('\'');
        query.append(record.get("creation_timestrmpz"));
        query.append('\'');
    }

}
