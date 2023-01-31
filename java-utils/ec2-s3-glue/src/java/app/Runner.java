package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private S3Writer writer;

    private final Logger logger;

    public Runner() {
        this.logger = LoggerFactory.getLogger(S3Writer.class);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Start write operation");

        writer.writeEc2OutToS3Out();

        logger.info("End write operation");
    }

}
