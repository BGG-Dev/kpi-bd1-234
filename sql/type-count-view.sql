-- Create view based on type-count query
DROP VIEW IF EXISTS type_count;
CREATE VIEW type_count AS
SELECT vote_type.type AS type, COUNT(votes) AS count
FROM vote_type
FULL OUTER JOIN votes ON vote_type.id = votes.vote_type_id
GROUP BY vote_type.type;