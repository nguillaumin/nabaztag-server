SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM `application` WHERE `application`.`application_id` > 0 ;
DELETE FROM `user` WHERE `user`.`user_id` = 1 LIMIT 1;
DELETE FROM `application_tag` WHERE `tag_id` > 0;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `user` VALUES (1,'jdgojfibicdcgngolgdeneiikginbonfxxxx','violet22','mpuyfaucher@violet.net',1,16951,9,14532,0,0,'84653822',1,0,113827693421946,1212593040,1,NULL,'0000FF','2005-11-28',0,30,1,'','');
INSERT INTO `application` (`application_id`, `user_id`, `application_name`, `application_creation_date`, `application_class`, `application_category_id`) VALUES (1, 1, 'WebUI', '2008-06-11 12:37:46', 'WEBUI', 1);
INSERT INTO `application` (`application_id`, `user_id`, `application_name`, `application_creation_date`, `application_class`, `application_category_id`) VALUES (2, 1, 'WebUI2', '2008-06-11 12:37:46', 'WEBUI', 2);

INSERT INTO `application_tag` VALUES (1, 'tagUn', 1, 20),(2, 'tagDeux', 1, 10), (3, 'tagTrois', 1, 30), (4, 'tagUn', 2, 20), (5, 'tagQuatre', 2, 20);