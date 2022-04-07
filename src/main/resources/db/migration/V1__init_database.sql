CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE users (
    username TEXT PRIMARY KEY NOT NULL,
    password TEXT NOT NULL,
    role TEXT
);

CREATE TABLE otp (
    username TEXT PRIMARY KEY NOT NULL,
    code TEXT
);