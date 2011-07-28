SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM `application` WHERE `application`.`application_id` > 0 ;
DELETE FROM `user` WHERE `user`.`user_id` = 1 LIMIT 1;
DELETE FROM `application_tag` WHERE `tag_id` > 0;

SET FOREIGN_KEY_CHECKS = 1;
