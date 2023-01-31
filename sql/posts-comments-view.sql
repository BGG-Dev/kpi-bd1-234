-- Create view based on posts-comments query
DROP VIEW IF EXISTS posts_comments;
CREATE VIEW posts_comments as
SELECT posts_by_user.count AS "post count",
       comments_by_user.count AS "comment count"
FROM (
    SELECT users.id AS user_id, COUNT(posts) AS count
    FROM posts
    FULL OUTER JOIN users ON users.id = posts.user_creator_id
    GROUP BY users.id
) AS posts_by_user
FULL OUTER JOIN (
    SELECT users.id AS user_id, COUNT(comments) AS count
    FROM comments
    FULL OUTER JOIN users ON users.id = comments.user_creator_id
    GROUP BY users.id
) AS comments_by_user
ON posts_by_user.user_id = comments_by_user.user_id;