-- Create view based on votes-by-users query
DROP VIEW IF EXISTS votes_by_users;
CREATE VIEW votes_by_users AS
SELECT users.id AS id,
       users.name AS name,
       COUNT(votes) AS "count of votes done by user"
FROM users
FULL OUTER JOIN votes ON votes.user_voter_id = users.id
GROUP BY users.id, users.name;