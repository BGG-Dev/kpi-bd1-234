-- Dropping tables if needed
DROP TABLE IF EXISTS user_credentials_logs;
DROP TABLE IF EXISTS user_credentials;

-- Create table user_credentials
CREATE TABLE user_credentials (
    id            BIGSERIAL    UNIQUE,
    user_id       BIGINT       UNIQUE NOT NULL,
    user_email    VARCHAR(256) UNIQUE NOT NULL,
    user_password VARCHAR(96)  UNIQUE NOT NULL,
    CONSTRAINT user_credentals_pk PRIMARY KEY (id)
);

-- user_credentials foreign key:
-- user_credentials.user_id -> users.id
ALTER TABLE user_credentials 
ADD CONSTRAINT user_credentials_fk0 
FOREIGN KEY (user_id) REFERENCES users(id);

-- Inserting user credentials directly
INSERT INTO user_credentials(user_id, user_email, user_password)
VALUES 
(72145   , 'nobnk49@itcdeganutti.it', '2Vp%yFbQdS*shCSC'),
(2443390 , 'mttv421@itcdeganutti.it', 'nWeDCWwnqUHYI@Zs'),
(1495971 , 'tzhiglova@gmailwe.com',   'pt^!yDXH&5Ck6YBq'),
(1521894 , 'kaiderk@luddo.me',        '2nT&T#^DYwR!Nk(+'),
(1399558 , 'faisalqb@gmailwe.com',    'sKFUF#f^96hB6XBM'),
(1774389 , 'dj1626@keitin.site',      '!&wtHm^kDt8bjWTy'),
(6459947 , 'predictum@otpku.com',     '3s3mS^HTg*Q+#jd!'),
(10696829, 'leonv14@alvinneo.com',    'pPgFbBSC!fuQf)Yq'),
(367407  , 'bohnenberger@emvil.com',  'xaXARvQU(W&)BfLw'),
(1125963 , 'sofic89@outlookbox.me',   'jG4fz2HZSgn*9c#k');

-- Create table user_credentials_logs
CREATE TABLE user_credentials_logs (
    id              BIGSERIAL                UNIQUE,
    user_subject_id BIGINT                   NOT NULL,
    operation       VARCHAR(1)               NOT NULL,
    took_place_at   TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT user_credentials_logs_pk PRIMARY KEY (id)
);

-- user_credentials_logs foreign key:
-- user_credentials_logs.user_subject_id -> users.id
ALTER TABLE user_credentials_logs
ADD CONSTRAINT user_credentials_fk0 
FOREIGN KEY (user_subject_id) REFERENCES user_credentials(id);
