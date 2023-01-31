-- query to show usage of each vote type
-- used to build circle chart
SELECT vote_type.type AS type, COUNT(votes) AS count
FROM vote_type
FULL OUTER JOIN votes ON vote_type.id = votes.vote_type_id
GROUP BY vote_type.type;
