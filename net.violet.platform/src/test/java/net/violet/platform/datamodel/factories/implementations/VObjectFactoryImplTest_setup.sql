SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO `user` VALUES (220,  'passfilip','Filipino', 'email1@filipines.ph', 1, -2,1,10165,1,1,'796',0,1,0,0,1,NULL,'FEAE23',NULL,1,30,1,'','');
INSERT INTO `annu` VALUES (220, 'H', 'cc', 'Varsovie', 'PL', 0, 1, '1980-05-12', 'Jan', 'Kowalski', '',NULL,36,NOW());

INSERT INTO `object` VALUES (18950,'08739D40299D8084','ObjFactImpl',220,1136281428,3,1156763864,0,3,14,0,2147483647,17,5 ,120,'unknow/unknow',0,0, 30);
INSERT INTO `object_preferences` VALUES (18950, 1,1,1);
INSERT INTO `object_profile` VALUES (18950, 'ObjFactImpl', null, 'private_description',null,null, NOW(), NOW());

INSERT INTO `object` VALUES (18951,'08739D40299D8085','ObjFactImpl2',220,1136281428,3,1156763864,0,3,14,0,2147483647,17,5,120,'unknow/unknow',0,0, 30);
INSERT INTO `object_preferences` VALUES (18951, 1,1,1);
INSERT INTO `object_profile` VALUES (18951, 'ObjFactImpl2', null, 'private_description',null,null, NOW(), NOW());

INSERT INTO `object` VALUES (18952,'08739D40299D8086','ObjFactImpl3',220,1136281428,3,1156763864,0,3,14,0,2147483647,17,5,120,'unknow/unknow',0,0, 30);
INSERT INTO `object_preferences` VALUES (18952, 0,1,1);
INSERT INTO `object_profile` VALUES (18952, 'ObjFactImpl3', null, 'private_description',null,null, NOW(), NOW());

INSERT INTO `object` VALUES (18953,'08739D40299D8087','ObFactImpl3',220,1136281428,3,1156763864,0,3,14,0,2147483647,17,5,120,'unknow/unknow',0,0, 30);
INSERT INTO `object_preferences` VALUES (18953, 1,1,1);
INSERT INTO `object_profile` VALUES (18953, 'ObctImpl', null, 'private_description',null,null, NOW(), NOW());

INSERT INTO `userLang` VALUES (220,1),(220,2);

SET FOREIGN_KEY_CHECKS = 1;
