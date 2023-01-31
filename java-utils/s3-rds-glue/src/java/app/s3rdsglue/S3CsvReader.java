package app.s3rdsglue;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.iterable.S3Objects;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class S3CsvReader {

    @Value("${app.s3bucket.name}")
    private String bucketName;

    private final AmazonS3 s3;

    private final Map<String, List<CSVRecord>> summaryContent;

    private final Logger logger;

    public S3CsvReader() {
        this.s3 = AmazonS3ClientBuilder.standard().build();
        this.summaryContent = new HashMap<>();
        this.logger = LoggerFactory.getLogger(S3CsvReader.class);
    }

    public Map<String, List<CSVRecord>> getCsvSummaryContent() {
        logger.info("Started reading from s3");

        S3Objects.withPrefix(s3, bucketName, "csv")
                 .forEach((S3ObjectSummary summary) -> {
            logger.info("Processing object " + summary.getKey());

            try {
                var file = s3.getObject(bucketName, summary.getKey());
                var tableName = summary.getKey().substring(summary.getKey().lastIndexOf('/') + 1,
                                                           summary.getKey().indexOf('.'));
                summaryContent.put(tableName, readAllRecordsFromCsv(file));

                logger.info("Captured object " + summary.getKey());
            } catch (IndexOutOfBoundsException e) {
                logger.info("Met unusual key: " + summary.getKey());
            }
        });

        logger.info("Captured " + summaryContent.size() + " objects");

        return summaryContent;
    }

    private List<CSVRecord> readAllRecordsFromCsv(S3Object file) {
        try (final CSVParser parser = CSVFormat.DEFAULT
                                               .withHeader()
                                               .parse(new InputStreamReader(file.getObjectContent()))) {
            return parser.getRecords();
        } catch (Exception e) {
            logger.error("Something went wrong during csv parsing");
        }

        return null;
    }

}
