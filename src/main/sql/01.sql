CREATE DATABASE IF NOT EXISTS db_sky_user_manager;

USE db_sky_user_manager;

--
-- Database cleanup
--
DROP TABLE IF EXISTS tb_user_external_projects;
DROP TABLE IF EXISTS tb_external_projects;
DROP TABLE IF EXISTS tb_users;

DROP VIEW IF EXISTS users;
DROP VIEW IF EXISTS authorities;

--
-- Table structure to store users
--
CREATE TABLE tb_users
(
id BIGINT NOT NULL COMMENT 'unique identifier of the user' AUTO_INCREMENT,
email VARCHAR(64) NOT NULL COMMENT 'email for user',
password VARCHAR(129) NOT NULL DEFAULT '123456' COMMENT 'password',
name VARCHAR(128) NULL,
role VARCHAR(16) NOT NULL,
PRIMARY KEY (id),
UNIQUE(email)
) COMMENT 'All users';

--
-- Table structure to store projects
--
CREATE TABLE tb_external_projects
(
id BIGINT NOT NULL COMMENT 'unique identifier of the project' AUTO_INCREMENT,
name VARCHAR(128) NOT NULL COMMENT 'name of external project',
PRIMARY KEY (id),
UNIQUE(name)
) COMMENT 'All projects';

--
-- Table structure to store one-to-many mapping for User.projects
--
CREATE TABLE tb_user_external_projects (
user_id BIGINT NOT NULL COMMENT 'unique identifier of the user',
project_id BIGINT NOT NULL COMMENT 'identifier of external project',
PRIMARY KEY (user_id, project_id),
FOREIGN KEY (user_id) REFERENCES tb_users(id),
FOREIGN KEY (project_id) REFERENCES tb_external_projects(id)
) COMMENT 'External Project identifier for users';


--
-- Views for Spring Security
--
CREATE VIEW users AS
SELECT email AS username, password, TRUE AS enabled
FROM tb_users;

CREATE VIEW authorities AS
SELECT email AS username, role AS authority
FROM tb_users;
