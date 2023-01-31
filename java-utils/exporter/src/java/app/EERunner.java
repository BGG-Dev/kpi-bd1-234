package app;

import app.ee.Extractor;
import app.ee.JsonExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class EERunner implements CommandLineRunner {

    @Autowired
    private Extractor extractor;

    @Autowired
    private JsonExporter exporter;

    @Override
    public void run(String... args) throws Exception {
        List<SqlRowSet> rowSets = new LinkedList<>();

        rowSets.add(extractor.process("users"));
        rowSets.add(extractor.process("posts"));
        rowSets.add(extractor.process("comments"));
        rowSets.add(extractor.process("votes"));
        rowSets.add(extractor.process("vote_type"));
        rowSets.add(extractor.process("votes_posts"));
        rowSets.add(extractor.process("votes_comments"));

        exporter.export2Json(rowSets);
    }

}
