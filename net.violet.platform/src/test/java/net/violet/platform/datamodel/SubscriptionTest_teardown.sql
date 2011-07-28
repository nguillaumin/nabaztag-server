SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM `user` WHERE user_id IN (195, 84887, 777, 12345, 123456);

DELETE FROM `object` WHERE object_id IN (31, 57520, 16951, 54321, 654321);

DELETE FROM `application` WHERE application_id IN (1119, 757);

DELETE FROM `subscription_settings` WHERE id BETWEEN 1 AND 3;

DELETE FROM `subscription_scheduling_settings` WHERE id BETWEEN 1 AND 32;

DELETE FROM `subscription_scheduling` WHERE id BETWEEN 1 AND 10;

DELETE FROM `subscription` WHERE id BETWEEN 1 AND 10;

SET FOREIGN_KEY_CHECKS = 1;

