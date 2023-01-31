-- Get all logs, which took place after given timestamp
CREATE OR REPLACE FUNCTION 
get_logs_after(time_edge TIMESTAMP WITH TIME ZONE)
RETURNS TABLE(id              BIGINT, 
              user_subject_id BIGINT, 
              operation       VARCHAR(1), 
              took_place_at   TIMESTAMP WITH TIME ZONE)
LANGUAGE 'plpgsql'
AS
$$
    BEGIN

        RETURN QUERY 
        SELECT * FROM user_credentials_logs
        WHERE user_credentials_logs.took_place_at > time_edge;

    END;
$$;
