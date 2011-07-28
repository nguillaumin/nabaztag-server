INSERT INTO `user` VALUES (1,'jdgojfibicdcgngolgdeneiikginbonfxxxx','violet22','mpuyfaucher@violet.net',1,16951,9,14532,0,0,'84653822',1,0,113827693421946,1212593040,1,NULL,'0000FF','2005-11-28',0,30,1,'','');
truncate application_category;
INSERT INTO `application_category` VALUES (1,'LOC_srv_category_daylife/title'),(2,'LOC_srv_category_news/title'),(3,'LOC_srv_category_finance/title'),(5,'LOC_srv_category_entertainement/title'),(9,'LOC_srv_category_culture/title'),(15,'LOC_srv_category_music/title'),(20,'LOC_srv_category_sports/title'),(22,'LOC_srv_category_science/title'),(23,'LOC_srv_category_lifestyle/title'),(24,'LOC_srv_category_kids/title'),(25,'LOC_srv_category_callback/title'),(26,'LOC_srv_category_perso/title');
INSERT INTO `application` (`application_id`, `user_id`, `application_name`, `application_creation_date`, `application_class`, `application_category_id`) VALUES (1, 1, 'WebUI', '2008-06-11 12:37:46', 'WEBUI', 1);
INSERT INTO `application` (`application_id`, `user_id`, `application_name`, `application_creation_date`, `application_class`, `application_category_id`) VALUES (2, 1, 'WebUI2', '2008-06-11 12:37:46', 'WEBUI', 2);

INSERT INTO `files` VALUES (1850108,'broadcast/broad/002/032/181/932.mp3', NOW(), '2007-12-03 18:19:26',8);

INSERT INTO `application_lang` VALUES (1,1,0);

INSERT INTO `application_tag` VALUES (1, 'tagUn', 1, 20),(2, 'tagDeux', 1, 20), (3, 'tagTrois', 1, 20), (4, 'tagUn', 2, 20), (5, 'tagQuatre', 2, 20);
INSERT INTO `application_has_tag` VALUES (1,1),(1,2),(2,2),(2,3),(2,4);

