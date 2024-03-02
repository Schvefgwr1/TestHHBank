CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY,
    login VARCHAR(127) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone_number VARCHAR(20),
    firstname VARCHAR(50),
    lastname VARCHAR(50),
    surname VARCHAR(50),
    date_of_birth DATE,
    price INTEGER NOT NULL
);