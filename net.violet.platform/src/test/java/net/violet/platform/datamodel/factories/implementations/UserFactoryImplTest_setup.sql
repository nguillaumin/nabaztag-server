SET FOREIGN_KEY_CHECKS = 0;
delete from `userLang` where user_id = '103' or user_id = '104' ;
delete from `object` where object_id = '16953' or object_id = '16952';
delete from `object_preferences` where object_id = '16953' or object_id = '16952';
delete from `user` where  user_id = '103' or user_id = '104';
delete from `annu` where annu_user = '103' or annu_user = '104';

INSERT INTO `user` VALUES (103,  "pass","Felix", "email1", 1, -2,1,10165,1,1,'796',0,1,0,0,1,NULL,'FFAE23',NULL,1,30,1,'','');
INSERT INTO `annu` VALUES (103, 'M', '', 'Varsovie', 'PL',0, 1, '1980-05-12', 'Janek', 'Kowalski','',NULL,36, NOW());
INSERT INTO `object` VALUES (16952,'00039D40299D8081','violet11',103,1136281428,3,1156763864,0,3,14,0,2147483647,17,5 ,120,'unknow/unknow',0,0, 30);
INSERT INTO `object_preferences` VALUES (16952, 1,1,1);

INSERT INTO `user` VALUES (104,  "pass","Maria", "email2", 1, -2,1,10165,1,1,'797',0,1,0,0,1,NULL,'FFAE23',NULL,1,30,1,'','');
INSERT INTO `annu` VALUES (104, 'F', '', 'Paris', 'FR', 0,  1, '1980-05-12', 'Izabella', 'Czartoryska', '',NULL,36,NOW());
INSERT INTO `object` VALUES (16953,'00039D40299D8082','violet22',104,1136281428,3,1156763864,0,3,14,0,2147483647,17,5 ,120,'unknow/unknow',0,0, 30);
INSERT INTO `object_preferences` VALUES (16953, 0,1,1);
INSERT INTO `userLang` VALUES (103,1),(103,2),(104,1),(104,2);
SET FOREIGN_KEY_CHECKS = 1;
