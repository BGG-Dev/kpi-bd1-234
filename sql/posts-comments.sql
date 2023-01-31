-- query to show corelation between post count of user and comment count of user
-- in other words, query returns function f(x),
-- where
-- x    - post count, done by user
-- f(x) - comment count, done by user
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
