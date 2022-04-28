CREATE TABLE product
(
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    price DOUBLE NOT NULL,
    description VARCHAR(255),
    pictureUrl VARCHAR(255)
);