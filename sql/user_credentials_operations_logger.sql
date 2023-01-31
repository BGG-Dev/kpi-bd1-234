-- Trigger to log update or insert operation on user_credentials table
CREATE OR REPLACE FUNCTION log_user_credentials_operation()
RETURNS TRIGGER
LANGUAGE 'plpgsql'
AS
$$
    BEGIN

        IF (TG_OP = 'UPDATE') THEN
            INSERT INTO user_credentials_logs
            (user_subject_id, operation, took_place_at)
            VALUES
            (NEW.id, 'U', CURRENT_TIMESTAMP);
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO user_credentials_logs
            (user_subject_id, operation, took_place_at)
            VALUES
            (NEW.id, 'I', CURRENT_TIMESTAMP);
        END IF;

        RETURN NULL;

    END;
$$;

CREATE OR REPLACE TRIGGER user_credentials_operation_logger
AFTER INSERT OR UPDATE ON user_credentials
FOR EACH ROW EXECUTE FUNCTION log_user_credentials_operation();
