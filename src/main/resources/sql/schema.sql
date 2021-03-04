CREATE TABLE user (
    id number AUTO_INCREMENT,
    login varchar(255),
    password varchar(255)
);
CREATE TABLE role (
    id number AUTO_INCREMENT,
    name varchar(5)
);
CREATE TABLE permission (
    id number AUTO_INCREMENT,
    user_role_id number,
    permission varchar(255),
    created_at datetime
);
CREATE TABLE user_role (
    id number AUTO_INCREMENT,
    user_id int,
    role_id int,
    created_at datetime
);