package app;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Stream;

@Component
public class S3Writer {

    @Value("${app.out-directory-ec2}")
    private String ec2OutDirectory;

    @Value("${app.s3bucket.name}")
    private String bucketName;

    @Value("${app.out-directory-s3}")
    private String s3OutDirectory;

    @Value("${app.region}")
    private String region;

    private S3Client s3;

    private final Logger logger;

    public S3Writer() {
        this.logger = LoggerFactory.getLogger(S3Writer.class);
    }

    @PostConstruct
    private void initializeS3() {
        this.s3 = S3Client.builder().region(Region.of(region)).build();
    }

    public void writeEc2OutToS3Out() throws IOException {
        String path = System.getProperty("user.home")
                    + File.separator
                    + ec2OutDirectory;

        logger.info("Reading files from directory: " + path);

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            Date current = new Date();
            paths.filter(Files::isRegularFile)
                 .forEach(f -> {
                     logger.info("Processing file: " + f.getFileName());

                     PutObjectRequest putOb = PutObjectRequest.builder()
                                                              .bucket(bucketName)
                                                              .key(s3OutDirectory
                                                                 + "/"
                                                                 + current.toString()
                                                                 + "-"
                                                                 + f.getFileName())
                                                              .build();
                     s3.putObject(putOb, RequestBody.fromBytes(getObjectFile(f.toFile())));

                     logger.info("Uploaded file" + f.getFileName());
                 });
        }
    }

    private byte[] getObjectFile(File file) {
        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {
            bytesArray = new byte[(int) file.length()];
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);
        } catch (IOException e) {
            logger.error("Can't read file " + file.getAbsolutePath());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    logger.error("Can't read file " + file.getAbsolutePath());
                }
            }
        }

        return bytesArray;
    }
}
