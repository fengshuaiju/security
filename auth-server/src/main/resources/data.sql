-- INSERT INTO oauth_client_details
-- 	(client_id, client_secret, scope, authorized_grant_types,
-- 	web_server_redirect_uri, authorities, access_token_validity,
-- 	refresh_token_validity, additional_information, autoapprove)
-- VALUES
-- 	('frontend', 'secret', 'foo,read,write,frontend',
-- 	'password,authorization_code,refresh_token', null, null, 36000, 36000, null, true);
-- INSERT INTO oauth_client_details
-- 	(client_id, client_secret, scope, authorized_grant_types,
-- 	web_server_redirect_uri, authorities, access_token_validity,
-- 	refresh_token_validity, additional_information, autoapprove)
-- VALUES
-- 	('sampleClientId', 'secret', 'read,write,foo,bar',
-- 	'implicit', null, null, 36000, 36000, null, false);
-- INSERT INTO oauth_client_details
-- 	(client_id, client_secret, scope, authorized_grant_types,
-- 	web_server_redirect_uri, authorities, access_token_validity,
-- 	refresh_token_validity, additional_information, autoapprove)
-- VALUES
-- 	('barClientIdPassword', 'secret', 'bar,read,write',
-- 	'password,authorization_code,refresh_token', null, null, 36000, 36000, null, true);


SELECT version();

INSERT IGNORE INTO users (id, created_at, username, name, nickname, sex, city, province, country, head_image_url, roles ) VALUES ('1000', CURRENT_TIMESTAMP , 'admin', '平台管理员', NULL, NULL, NULL, NULL, NULL, NULL, 'TENANT_ADMIN');

INSERT IGNORE INTO login (id, created_at, username, password, cellphone, email_address, wechat_open_id, user_id ) VALUES ('1000', CURRENT_TIMESTAMP , 'admin', '{bcrypt}$2a$10$nLQaW2BQz79.aO2jdqHVdO4tfBZZlDC2rg.vRXQnWktIDI4j3YtAu', NULL, NULL, NULL, '1000');

--
-- INSERT INTO users (
--   id, nickname, first_name, middle_name, last_name, sex, city, province, country, username
-- ) VALUES (
--   1000, 'Administrator', 'administrator', 'administrator', 'administrator', 'MALE',
--   'shanghai', 'shanghai', 'china', 'u_test_admin'
-- );
--
-- INSERT INTO login (
-- 	id, username, password, cellphone, wechat_open_id, user_id
-- ) VALUES (
-- 	1000, 'u_test_admin', 'password', '13812345678', 'test12345678901234567890', 1000
-- );
--
-- INSERT INTO membership (
-- 	id, version, username, tenant_id, staff_email_address, staff_first_name, staff_last_name, roles,enabled
-- ) VALUES (
-- 	1000, 0, 'u_test_admin', '0e11053b-e2d7-4835-a17b-be740a306aaf', 'admin@test.com',
-- 	'Katrina', 'Xu', 'PLATFORM_TENANT_ACCOUNT_MANAGEMENT',TRUE
-- );
