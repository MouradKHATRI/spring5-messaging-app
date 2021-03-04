CREATE TABLE user (
    id int,
    login varchar(255),
    password varchar(255)
);
CREATE TABLE role (
    id int,
    name varchar(255)
);
CREATE TABLE permission (
    id int,
    user_role_id int,
    permission varchar(255),
    created_at datetime
);
CREATE TABLE user_role (
    id int,
    user_id int,
    role_id int,
    created_at datetime
);