INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity, refresh_token_validity, autoapprove)
VALUES ('frontend', 'secret', 'frontend', 'client_credentials', 3600 * 24 * 30, NULL, TRUE);
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity, refresh_token_validity, autoapprove)
VALUES ('user', 'secret', 'user', 'password,refresh_token', 3600 * 8, 3600 * 24 * 14, TRUE);
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity, refresh_token_validity, autoapprove)
VALUES ('platform', 'secret', 'platform', 'password,refresh_token', 3600 * 8, 3600 * 24 * 14, TRUE);
