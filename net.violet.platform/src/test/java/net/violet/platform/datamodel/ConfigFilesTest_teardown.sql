SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM `files` WHERE id != 1;
TRUNCATE `config_files`;

DELETE FROM `application` WHERE `application_name` IN ('net.violet.air', 'net.violet.weather', 'net.violet.bilan', 'net.violet.trafic', 'net.violet.bourse', 'net.violet.clock');

SET FOREIGN_KEY_CHECKS = 1;