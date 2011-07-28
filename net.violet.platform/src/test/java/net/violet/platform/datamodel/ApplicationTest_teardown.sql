truncate application_profile;
DELETE FROM `application` WHERE `application`.`application_id` > 0 ;
DELETE FROM `user` WHERE `user`.`user_id` = 1 LIMIT 1;

delete from `files` where id=1850108;

delete from `application_lang` WHERE `application_id` > 0;

DELETE FROM `application_tag` WHERE `tag_id` > 0;
DELETE FROM `application_has_tag` WHERE `tag_id` > 0;