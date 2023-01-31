-- Deletes all logs, which took place before given datetime
CREATE OR REPLACE PROCEDURE 
delete_logs_before(time_edge TIMESTAMP WITH TIME ZONE)
LANGUAGE 'plpgsql'
AS
$$
    BEGIN

        DELETE FROM user_credentials_logs
        WHERE user_credentials_logs.took_place_at < time_edge;

    END;
$$;
