package app;

import app.analyticsrunner.PostsCommentsAnalytics;
import app.analyticsrunner.TypeCountAnalytics;
import app.analyticsrunner.VoteCountByUserAnalytics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsRunner implements CommandLineRunner {

    @Autowired
    private PostsCommentsAnalytics postsCommentsAnalytics;

    @Autowired
    private TypeCountAnalytics typeCountAnalytics;

    @Autowired
    private VoteCountByUserAnalytics voteCountByUserAnalytics;

    private final Logger logger;

    public AnalyticsRunner() {
        this.logger = LoggerFactory.getLogger(AnalyticsRunner.class);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Started analytics");

        postsCommentsAnalytics.process();
        typeCountAnalytics.process();
        voteCountByUserAnalytics.process();

        logger.info("End analytics");
    }

}
