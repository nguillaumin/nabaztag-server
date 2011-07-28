SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM `user` WHERE user_id > 0;

DELETE FROM `object` WHERE object_id > 0;

DELETE FROM `application` WHERE application_id > 0;

DELETE FROM `subscription_scheduling` WHERE id > 0;

DELETE FROM `subscription` WHERE id  > 0;

SET FOREIGN_KEY_CHECKS = 1;

