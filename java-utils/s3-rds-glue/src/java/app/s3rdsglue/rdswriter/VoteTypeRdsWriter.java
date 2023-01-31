package app.s3rdsglue.rdswriter;

import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VoteTypeRdsWriter extends AbstractRdsWriter {

    @Autowired
    public VoteTypeRdsWriter(JdbcTemplate jdbc) {
        super(jdbc);
    }

    @Override
    void buildInsertValue(StringBuilder query, CSVRecord record) {
        query.append(record.get("id"));
        query.append(',');
        query.append('\'');
        query.append(record.get("type"));
        query.append('\'');
    }

}
