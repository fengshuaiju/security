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
  expiresAt      TIMESTAMP,
  lastModifiedAt TIMESTAMP
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

CREATE TABLE IF NOT EXISTS tenant (
  id                      BIGINT(11) AUTO_INCREMENT PRIMARY KEY,
  created_at              TIMESTAMP DEFAULT current_timestamp NOT NULL,
  tenant_id               VARCHAR(64)                                  NOT NULL UNIQUE,
  owner_id                VARCHAR(150)                                  NOT NULL,
  supporter_id            VARCHAR(150),
  chinese_name            VARCHAR(150)                                  NOT NULL UNIQUE,
  english_name            VARCHAR(150) UNIQUE,
  office_address          VARCHAR(150),
  office_phone            VARCHAR(150),
  fax_number              VARCHAR(150),
  postcode                VARCHAR(150),
  business_license_number VARCHAR(150) UNIQUE,
  business_license_uri    VARCHAR(150),
  organization_code_uri   VARCHAR(150),
  tax_number_uri          VARCHAR(150),
  status                  VARCHAR(150)                                  NOT NULL,
  type                    VARCHAR(150),
  code                    VARCHAR(8),
  reviewed_at             TIMESTAMP,
  review_remarks          VARCHAR(150),

  CONSTRAINT FK_tenant_owner_id FOREIGN KEY (owner_id) REFERENCES users (username),
  CONSTRAINT FK_tenant_supporter_id FOREIGN KEY (supporter_id) REFERENCES users (username)
);

CREATE TABLE IF NOT EXISTS membership (
  id            BIGINT(11) AUTO_INCREMENT PRIMARY KEY,
  version       BIGINT(11),
  created_at    TIMESTAMP DEFAULT current_timestamp NOT NULL,

  username      VARCHAR(150)                                  NOT NULL,
  tenant_id     VARCHAR(64)                                  NOT NULL,
  name          VARCHAR(150),
  cellphone     VARCHAR(150),
  email_address VARCHAR(150),
  remarks       VARCHAR(150),
  enabled       BOOLEAN                               NOT NULL,

  UNIQUE (username, tenant_id),
  CONSTRAINT FK_membership_username FOREIGN KEY (username) REFERENCES login (username),
  CONSTRAINT FK_membership_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenant (tenant_id)
);

CREATE TABLE IF NOT EXISTS membership_role_group_ids (
  membership_id BIGINT(11) NOT NULL,
  role_group_id VARCHAR(64)    NOT NULL,

  PRIMARY KEY (membership_id, role_group_id),
  CONSTRAINT FK_membership_role_group_ids_membership_id FOREIGN KEY (membership_id) REFERENCES membership (id)
);

CREATE TABLE IF NOT EXISTS role_group (
  id        VARCHAR(64) NOT NULL PRIMARY KEY,
  version   BIGINT(11),
  tenant_id VARCHAR(64)   NOT NULL,
  name      VARCHAR(150) NOT NULL,

  UNIQUE (tenant_id, name),
  CONSTRAINT FK_role_group_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenant (tenant_id)
);

CREATE TABLE IF NOT EXISTS role_group_roles (
  role_group_id VARCHAR(64)  NOT NULL,
  role          VARCHAR(150) NOT NULL,

  PRIMARY KEY (role_group_id, role),
  CONSTRAINT FK_role_group_roles_role_group_id FOREIGN KEY (role_group_id) REFERENCES role_group (id)
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
