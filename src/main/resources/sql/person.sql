CREATE TABLE person (
                        id  int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                        username VARCHAR(30) NOT NULL,
                        password VARCHAR(100),
                        email VARCHAR(100),
                        role VARCHAR(100)
);