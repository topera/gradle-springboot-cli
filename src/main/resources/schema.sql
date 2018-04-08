-- DROP TABLES
DROP TABLE IF EXISTS blacklist;
DROP TABLE IF EXISTS log;
DROP TABLE IF EXISTS ip;

-- CREATE TABLES
CREATE TABLE ip (
    id          INT NOT NULL AUTO_INCREMENT,
    ip_address  VARCHAR(40) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE log (
    id          INT NOT NULL AUTO_INCREMENT,
    ip_id       INT NOT NULL,
    date        DATETIME(6) NOT NULL,
    request     VARCHAR(40),
    status      VARCHAR(10),
    userAgent   VARCHAR(1024),
    PRIMARY KEY (id),
    FOREIGN KEY (ip_id) REFERENCES ip(id)
);

CREATE TABLE blacklist (
    ip_id       INT NOT NULL,
    comment     VARCHAR(2000) NOT NULL,
    PRIMARY KEY (ip_id),
    FOREIGN KEY (ip_id) REFERENCES ip(id)
);

