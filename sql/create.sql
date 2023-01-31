-- removing foreign keys
ALTER TABLE IF EXISTS votes DROP CONSTRAINT votes_fk0;
ALTER TABLE IF EXISTS votes DROP CONSTRAINT votes_fk1;

ALTER TABLE IF EXISTS posts DROP CONSTRAINT posts_fk0;

ALTER TABLE IF EXISTS comments DROP CONSTRAINT comments_fk0;
ALTER TABLE IF EXISTS comments DROP CONSTRAINT comments_fk1;

ALTER TABLE IF EXISTS votes_posts DROP CONSTRAINT votes_posts_fk0;
ALTER TABLE IF EXISTS votes_posts DROP CONSTRAINT votes_posts_fk1;

ALTER TABLE IF EXISTS votes_comments DROP CONSTRAINT votes_comments_fk0;
ALTER TABLE IF EXISTS votes_comments DROP CONSTRAINT votes_comments_fk1;

-- dropping tables
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS vote_type;
DROP TABLE IF EXISTS votes_posts;
DROP TABLE IF EXISTS votes_comments;


-- users table
CREATE TABLE users (
	id                      BIGINT                   NOT NULL,
	name                    varchar(255)             NOT NULL,
	registration_timestrmpz timestamp with time zone NOT NULL,
	last_access_timestrmpz  timestamp with time zone NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);

-- votes table
CREATE TABLE votes (
	id                  BIGINT                   NOT NULL,
	user_voter_id       BIGINT                   NOT NULL,
	vote_type_id        BIGINT                   NOT NULL,
	creation_timestrmpz timestamp with time zone NOT NULL,
	CONSTRAINT vote_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);

-- vote_type table
CREATE TABLE vote_type (
	id   BIGINT      NOT NULL,
	type varchar(10) NOT NULL UNIQUE,
	CONSTRAINT vote_type_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);

-- posts table
CREATE TABLE posts (
	id                       BIGINT                   NOT NULL,
	title                    varchar(255)             NOT NULL,
	body                     TEXT                     NOT NULL,
	user_creator_id          BIGINT                   NOT NULL,
	creation_timestrmpz      timestamp with time zone NOT NULL,
	last_activity_timestrmpz timestamp with time zone NOT NULL,
	CONSTRAINT post_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);

-- comments table
CREATE TABLE comments (
	id                  BIGINT                   NOT NULL,
	body                TEXT                     NOT NULL,
	post_id             BIGINT                   NOT NULL,
	user_creator_id     BIGINT                   NOT NULL,
	creation_timestrmpz timestamp with time zone NOT NULL,
	CONSTRAINT comment_pk PRIMARY KEY (id)
) WITH (
  OIDS=FALSE
);

-- votes_posts
CREATE TABLE votes_posts (
	vote_id BIGINT NOT NULL UNIQUE,
	post_id BIGINT NOT NULL
) WITH (
  OIDS=FALSE
);

-- votes_comments
CREATE TABLE votes_comments (
	vote_id    BIGINT NOT NULL UNIQUE,
	comment_id BIGINT NOT NULL
) WITH (
  OIDS=FALSE
);


-- votes foreign keys:
--     user_voter_id -> users.id
--     vote_type_id  -> vote_type.id
ALTER TABLE votes ADD CONSTRAINT votes_fk0 FOREIGN KEY (user_voter_id) REFERENCES users(id);
ALTER TABLE votes ADD CONSTRAINT votes_fk1 FOREIGN KEY (vote_type_id) REFERENCES vote_type(id);

-- posts foreign keys:
--     user_creator_id -> users.id
ALTER TABLE posts ADD CONSTRAINT posts_fk0 FOREIGN KEY (user_creator_id) REFERENCES users(id);

-- comments foreign keys:
--     post_id         -> posts.id
--     user_creator_id -> users.id
ALTER TABLE comments ADD CONSTRAINT comments_fk0 FOREIGN KEY (post_id) REFERENCES posts(id);
ALTER TABLE comments ADD CONSTRAINT comments_fk1 FOREIGN KEY (user_creator_id) REFERENCES users(id);

-- votes_posts foreign keys (obvious)
ALTER TABLE votes_posts ADD CONSTRAINT votes_posts_fk0 FOREIGN KEY (vote_id) REFERENCES votes(id);
ALTER TABLE votes_posts ADD CONSTRAINT votes_posts_fk1 FOREIGN KEY (post_id) REFERENCES posts(id);

-- votes_comments foreign keys (obvious)
ALTER TABLE votes_comments ADD CONSTRAINT votes_comments_fk0 FOREIGN KEY (vote_id) REFERENCES votes(id);
ALTER TABLE votes_comments ADD CONSTRAINT votes_comments_fk1 FOREIGN KEY (comment_id) REFERENCES comments(id);
