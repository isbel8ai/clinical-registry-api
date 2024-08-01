INSERT INTO ACCOUNT (FULL_NAME, EMAIL, PASSWORD, ROLE_ID)
VALUES ('Administrator', '${admin_username}', '${admin_password}', (SELECT id FROM ROLE WHERE NAME = 'ADMIN'));