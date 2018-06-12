--------------- Postgres ---------------

DROP TABLE IF EXISTS oauth_client_details;
CREATE TABLE oauth_client_details (
  client_id               VARCHAR(150) PRIMARY KEY,
  resource_ids            VARCHAR(150),
  client_secret           VARCHAR(150),
  scope                   VARCHAR(150),
  authorized_grant_types  VARCHAR(150),
  web_server_redirect_uri VARCHAR(150),
  authorities             VARCHAR(150),
  access_token_validity   INTEGER,
  refresh_token_validity  INTEGER,
  additional_information  VARCHAR(150),
  autoapprove             VARCHAR(150) 
);

CREATE TABLE IF NOT EXISTS oauth_client_token (
  token_id          VARCHAR(150),
  token             LONGBLOB,
  authentication_id VARCHAR(150) PRIMARY KEY,
  user_name         VARCHAR(150),
  client_id         VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS oauth_access_token (
  token_id          VARCHAR(150),
  token             LONGBLOB,
  authentication_id VARCHAR(150) PRIMARY KEY,
  user_name         VARCHAR(150),
  client_id         VARCHAR(150),
  authentication    LONGBLOB,
  refresh_token     VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS oauth_refresh_token (
  token_id       VARCHAR(150),
  token          LONGBLOB,
  authentication LONGBLOB
);

CREATE TABLE IF NOT EXISTS oauth_code (
  code           VARCHAR(150),
  authentication LONGBLOB
);

CREATE TABLE IF NOT EXISTS oauth_approvals (
  userId         VARCHAR(150),
  clientId       VARCHAR(150),
  scope          VARCHAR(150),
  status         VARCHAR(150),
  expiresAt      TIMESTAMP DEFAULT current_timestamp NOT NULL,
  lastModifiedAt TIMESTAMP DEFAULT current_timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS client_details (
  appId                  VARCHAR(150) PRIMARY KEY,
  resourceIds            VARCHAR(150),
  appSecret              VARCHAR(150),
  scope                  VARCHAR(150),
  grantTypes             VARCHAR(150),
  redirectUrl            VARCHAR(150),
  authorities            VARCHAR(150),
  access_token_validity  INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation  VARCHAR(150),
  autoApproveScopes      VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS users (
  id             BIGINT(11) AUTO_INCREMENT PRIMARY KEY,
  created_at     TIMESTAMP DEFAULT current_timestamp NOT NULL,

  username       VARCHAR(150)                                  NOT NULL UNIQUE,
  name           VARCHAR(150),
  nickname       VARCHAR(150),
  roles          VARCHAR(150),
  sex            VARCHAR(150),
  city           VARCHAR(150),
  province       VARCHAR(150),
  country        VARCHAR(150),
  head_image_url VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS login (
  id             BIGINT(11) AUTO_INCREMENT PRIMARY KEY,
  created_at     TIMESTAMP DEFAULT current_timestamp NOT NULL,
  username       VARCHAR(150)                                  NOT NULL UNIQUE,
  password       VARCHAR(150),
  cellphone      VARCHAR(150) UNIQUE,
  email_address  VARCHAR(150) UNIQUE,
  wechat_open_id VARCHAR(150) UNIQUE,
  user_id        BIGINT(11),

  CONSTRAINT FK_login_user FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT FK_login_username FOREIGN KEY (username) REFERENCES users (username)
);


CREATE TABLE IF NOT EXISTS event_to_publish (
  id      BIGINT(11) AUTO_INCREMENT PRIMARY KEY,
  name    VARCHAR(150) NOT NULL,
  content VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS event_publishing_tracker (
  id                             BIGINT(11) AUTO_INCREMENT PRIMARY KEY,
  most_recent_published_event_id BIGINT(11),

  CONSTRAINT FK_event_publishing_tracker_most_recent_published_event_id FOREIGN KEY (most_recent_published_event_id) REFERENCES event_to_publish (id)
);

CREATE TABLE IF NOT EXISTS verification_code (
  id        VARCHAR(150)        NOT NULL PRIMARY KEY,
  captcha   VARCHAR(150)        NOT NULL,
  expire_at TIMESTAMP NOT NULL
);

--------------- MySQL ---------------
--drop table if exists oauth_client_details;
--create table oauth_client_details (
--  client_id VARCHAR(150)(255) PRIMARY KEY,
--  resource_ids VARCHAR(150)(255),
--  client_secret VARCHAR(150)(255),
--  scope VARCHAR(150)(255),
--  authorized_grant_types VARCHAR(150)(255),
--  web_server_redirect_uri VARCHAR(150)(255),
--  authorities VARCHAR(150)(255),
--  access_token_validity INTEGER,
--  refresh_token_validity INTEGER,
--  additional_information VARCHAR(150)(4096),
--  autoapprove VARCHAR(150)(255)
--);
--
--create table if not exists oauth_client_token (
--  token_id VARCHAR(150)(255),
--  token LONG VARBINARY,
--  authentication_id VARCHAR(150)(255) PRIMARY KEY,
--  user_name VARCHAR(150)(255),
--  client_id VARCHAR(150)(255)
--);
--
--create table if not exists oauth_access_token (
--  token_id VARCHAR(150)(255),
--  token LONG VARBINARY,
--  authentication_id VARCHAR(150)(255) PRIMARY KEY,
--  user_name VARCHAR(150)(255),
--  client_id VARCHAR(150)(255),
--  authentication LONG VARBINARY,
--  refresh_token VARCHAR(150)(255)
--);
--
--create table if not exists oauth_refresh_token (
--  token_id VARCHAR(150)(255),
--  token LONG VARBINARY,
--  authentication LONG VARBINARY
--);
--
--create table if not exists oauth_code (
--  code VARCHAR(150)(255), authentication LONG VARBINARY
--);
--
--create table if not exists oauth_approvals (
--	userId VARCHAR(150)(255),
--	clientId VARCHAR(150)(255),
--	scope VARCHAR(150)(255),
--	status VARCHAR(150)(10),
--	expiresAt TIMESTAMP,
--	lastModifiedAt TIMESTAMP
--);
--
--create table if not exists ClientDetails (
--  appId VARCHAR(150)(255) PRIMARY KEY,
--  resourceIds VARCHAR(150)(255),
--  appSecret VARCHAR(150)(255),
--  scope VARCHAR(150)(255),
--  grantTypes VARCHAR(150)(255),
--  redirectUrl VARCHAR(150)(255),
--  authorities VARCHAR(150)(255),
--  access_token_validity INTEGER,
--  refresh_token_validity INTEGER,
--  additionalInformation VARCHAR(150)(4096),
--  autoApproveScopes VARCHAR(150)(255)
--);
