insert into master.user_security (ID, USERNAME, old_password, name, EMAIL, ENABLED, LASTPASSWORDRESETDATE) VALUES 
('SECURITY-USER-ADMIN001', 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 'admin@admin.com', true, '01-01-2016');

insert into master.authority (ID, NAME, ROLENAME) VALUES ('SECURITY-AUTHORITY-USER', 'ROLE_USER', 'Pengguna');
insert into master.authority (ID, NAME, ROLENAME) VALUES ('SECURITY-AUTHORITY-ADMIN', 'ROLE_ADMIN', 'Pentadbir');
insert into master.authority (ID, NAME, ROLENAME) VALUES ('SECURITY-AUTHORITY-COACH', 'ROLE_COACH', 'Coach');
insert into master.authority (ID, NAME, ROLENAME) VALUES ('SECURITY-AUTHORITY-SUPERVISOR', 'ROLE_SUPERVISOR', 'Penyelia');

insert into master.user_authorities (ID, USER_ID, AUTHORITY_ID) VALUES ('SECURITY-USER-AUTH-001', 'SECURITY-USER-ADMIN001', 'SECURITY-AUTHORITY-USER');
insert into master.user_authorities (ID, USER_ID, AUTHORITY_ID) VALUES ('SECURITY-USER-AUTH-002', 'SECURITY-USER-ADMIN001', 'SECURITY-AUTHORITY-ADMIN');
