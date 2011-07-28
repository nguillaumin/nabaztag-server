SET FOREIGN_KEY_CHECKS = 0;
DELETE FROM user where user_pseudo = 'private';
DELETE FROM user where user_pseudo = 'kowalsky';
DELETE FROM object where object_login = 'private';
DELETE FROM object where object_login = 'kowalsky';
DELETE FROM messenger where name LIKE 'private%';
DELETE FROM messenger where name LIKE 'kowalsky%';
DELETE FROM music where music_id = '10163';
SET FOREIGN_KEY_CHECKS = 1;