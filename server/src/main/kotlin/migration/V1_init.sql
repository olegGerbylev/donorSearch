CREATE TABLE accounts(
    id  BIGINT  NOT NULL PRIMARY KEY,

    display_name    TEXT NOT NULL,
    email   TEXT    NOT NULL,
    password    TEXT    NOT NULL,

    active BOOLEAN NOT NULL DEFAULT TRUE,
    confirmed BOOLEAN NOT NULL DEFAULT FALSE,

    permission TEXT NOT NULL DEFAULT 'user',

    last_active TIMESTAMP NOT NULL,
    creation_date TIMESTAMP NOT NULL
);

CREATE SEQUENCE account_id_seq
    START 1;

CREATE TABLE token_confirmation(
    id BIGINT NOT NULL,
    token   text    NOT NULL PRIMARY KEY UNIQUE,
    expire_time TIMESTAMP NOT NULL,
    FOREIGN KEY (id) REFERENCES accounts(id) ON DELETE CASCADE
);


CREATE TABLE sessions(
    session_id  TEXT NOT NULL PRIMARY KEY,
    account_id BIGINT NOT NULL,

    super_admin BOOLEAN NOT NULL DEFAULT FALSE,

    deleted_ts TIMESTAMP NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE TABLE map_points(
    id BIGINT NOT NULL PRIMARY KEY,
    tal FLOAT NOT NULL,
    lng FLOAT NOT NULL
);

CREATE SEQUENCE map_point_id_seq
    START 1;

CREATE TABLE map_point_info(
    id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(25) NOT NULL,
    info TEXT,
    important TEXT,
    time VARCHAR(50),
    photo VARCHAR(255)
);
















CREATE TABLE features(
    id BIGINT PRIMARY KEY,
    feature TEXT NOT NULL
);


CREATE TABLE account_features(
    account_id BIGINT NOT NULL REFERENCES accounts ON DELETE CASCADE,
    feature_id BIGINT NOT NULL REFERENCES features ON DELETE CASCADE,

    PRIMARY KEY(account_id, feature_id)
);
