package app.analyticsrunner;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class TypeCountAnalytics extends AbstractAnalytics {

    private final Logger logger;

    @Value("${app.output-directory}")
    private String outputDirectory;

    private Writer writer;

    private CSVPrinter printer;

    @Autowired
    public TypeCountAnalytics(JdbcTemplate jdbc) {
        super(jdbc, """
                SELECT * FROM type_count;
                """);
        this.logger = LoggerFactory.getLogger(TypeCountAnalytics.class);
    }

    @PostConstruct
    private void initializeCsvUtils() throws IOException {
        String path = System.getProperty("user.home")
                    + File.separator
                    + outputDirectory
                    + File.separator
                    + "type-count.csv";
        File f = new File(path);
        f.createNewFile();
        writer = Files.newBufferedWriter(Paths.get(path));
        printer = CSVFormat.DEFAULT.withHeader("type", "count").print(writer);
    }

    @PreDestroy
    private void finalizeCsvUtils() throws IOException {
        printer.flush();
        writer.close();
    }

    @Override
    void showRow(SqlRowSet rows) {
        try {
            printer.printRecord(rows.getString("type"), rows.getLong("count"));
        } catch (IOException e) {
            logger.error("IOException during writing row" + rows);
        }
    }

}
