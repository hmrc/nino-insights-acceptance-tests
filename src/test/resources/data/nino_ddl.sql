DROP TABLE IF EXISTS __schema__.nino_reject;
CREATE TABLE __schema__.nino_reject
(
    nino              TEXT      NOT NULL,
    requested_because TEXT      NOT NULL,
    requested_at      TIMESTAMP NOT NULL,
    requested_by      TEXT      NOT NULL,
    PRIMARY KEY (nino)
    );

CREATE INDEX IF NOT EXISTS __schema___nino_reject_ninoIdx on __schema__.nino_reject(nino);
