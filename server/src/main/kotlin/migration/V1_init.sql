CREATE TABLE accounts(
    id  BIGINT  NOT NULL PRIMARY KEY,

    email   TEXT    NOT NULL,
    password    TEXT    NOT NULL,

    active BOOLEAN NOT NULL DEFAULT TRUE,
    confirmed BOOLEAN NOT NULL DEFAULT FALSE,

    role TEXT NOT NULL DEFAULT 'user',

    creation_date TIMESTAMP NOT NULL,

    display_name    TEXT NOT NULL,
    photo TEXT,
    phone TEXT,
    city TEXT,
    bio TEXT
);

CREATE SEQUENCE account_id_seq
    START 1;

CREATE TABLE tokens_confirmation(
    id BIGINT NOT NULL,
    token   text    NOT NULL PRIMARY KEY UNIQUE,
    deleted_ts TIMESTAMP NOT NULL,
    FOREIGN KEY (id) REFERENCES accounts(id) ON DELETE CASCADE
);


CREATE TABLE sessions(
    session_id  TEXT NOT NULL PRIMARY KEY,
    account_id BIGINT NOT NULL,

--    super_admin BOOLEAN NOT NULL DEFAULT FALSE,

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
    name TEXT NOT NULL,
    address TEXT NOT NULL,
    phone TEXT NOT NULL,
    info TEXT,
    important TEXT,
    photo TEXT,
    FOREIGN KEY (id) REFERENCES map_points(id) ON DELETE CASCADE
);

CREATE TABLE map_point_work_time(
    id BIGINT NOT NULL,
    mon TEXT,
    tue TEXT,
    wed TEXT,
    thu TEXT,
    fri TEXT,
    sat TEXT,
    sun TEXT,
    FOREIGN KEY (id) REFERENCES map_points(id) ON DELETE CASCADE
);

CREATE TABLE pets(
    id BIGINT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    type TEXT NOT NULL,
    confirmed BOOLEAN DEFAULT FALSE,
    age INT,
    weight INT,
    name TEXT,
    photo TEXT,
    passport TEXT,
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE SEQUENCE pet_id_seq
    START 1;

CREATE TABLE pet_donations(
    id BIGINT PRIMARY KEY,
    pet_id BIGINT NOT NULL,
    point_id BIGINT NOT NULL,

    donation_data TIMESTAMP NOT NULL,

    FOREIGN KEY (pet_id) REFERENCES pets(id)
);

CREATE SEQUENCE pet_donations_id_seq
    START 1;

CREATE TABLE news(
    id BIGINT NOT NULL PRIMARY KEY,
    label TEXT NOT NULL,
    photo TEXT,
    summer TEXT NOT NULL,
    theme TEXT NOT NULL,
    creation_ts TIMESTAMP NOT NULL
);

CREATE TABLE news_text(
    news_id BIGINT NOT NULL,
    main_text TEXT NOT NULL,
    FOREIGN KEY (news_id) REFERENCES news(id) ON DELETE CASCADE
);

CREATE SEQUENCE news_id_seq
    START 1;
