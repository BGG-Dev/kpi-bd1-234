-- Inserting back with loop
DO $$

    DECLARE
        vote_types VARCHAR(10)[]; -- array to contain types of votes
    BEGIN

    -- Removing votes.vote_type_id -> vote_type.id constraint
    -- to delete data from vote_type
    ALTER TABLE votes DROP CONSTRAINT IF EXISTS votes_fk1;
    
    -- Deleting all rows from vote_type table
    DELETE FROM vote_type;
    
    -- Initialize types of votes
    vote_types = '{like, dislike, interest, love, cringe}';
    
    -- Loop
    FOR i IN 1..5
    LOOP
        -- Inserting into vote_type table
        INSERT INTO vote_type(id, type) VALUES(i, vote_types[i]);
    END LOOP;
    
    -- Recreating foreign key votes.vote_type_id -> vote_type.id
    ALTER TABLE votes ADD CONSTRAINT votes_fk1 FOREIGN KEY (vote_type_id) REFERENCES vote_type(id);

    END;

$$