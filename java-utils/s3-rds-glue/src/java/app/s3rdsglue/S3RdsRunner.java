package app.s3rdsglue;

import app.s3rdsglue.rdswriter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class S3RdsRunner implements CommandLineRunner {

    @Autowired
    private S3CsvReader s3CsvReader;

    @Autowired
    private UsersRdsWriter usersRdsWriter;

    @Autowired
    private VotesRdsWriter votesRdsWriter;

    @Autowired
    private VoteTypeRdsWriter voteTypeRdsWriter;

    @Autowired
    private PostsRdsWriter postsRdsWriter;

    @Autowired
    private CommentsRdsWriter commentsRdsWriter;

    @Autowired
    private VotesPostsRdsWriter votesPostsRdsWriter;

    @Autowired
    private VotesCommentsRdsWriter votesCommentsRdsWriter;

    private final Logger logger;

    public S3RdsRunner() {
        this.logger = LoggerFactory.getLogger(S3CsvReader.class);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Start s3-rds runner");

        var data = s3CsvReader.getCsvSummaryContent();

        usersRdsWriter.insertIntoRds("users", data.get("users"));
        voteTypeRdsWriter.insertIntoRds("vote_type", data.get("vote_type"));
        postsRdsWriter.insertIntoRds("posts", data.get("posts"));
        commentsRdsWriter.insertIntoRds("comments", data.get("comments"));
        votesRdsWriter.insertIntoRds("votes", data.get("votes"));
        votesPostsRdsWriter.insertIntoRds("votes_posts", data.get("votes_posts"));
        votesCommentsRdsWriter.insertIntoRds("votes_comments", data.get("votes_comments"));

        logger.info("End s3-rds runner");
    }

}
