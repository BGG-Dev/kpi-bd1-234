-- query to show count of votes, done by each user
-- used to build column chart
SELECT users.id AS id,
       users.name AS name,
       COUNT(votes) AS "count of votes done by user"
FROM users
FULL OUTER JOIN votes ON votes.user_voter_id = users.id
GROUP BY users.id, users.name;
