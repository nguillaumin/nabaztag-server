SET FOREIGN_KEY_CHECKS = 0;

delete from `object_preferences` where object_id=31;
delete from `object_profile` where object_id=31;
delete from `user` where user_id = '1';
delete from `object` where object_id = '31';
delete from files where id in(1850108,1850109);

INSERT INTO `files` VALUES (1850108,'', '', 'broadcast/broad/002/032/181/932.jpeg', 'image/jpeg', NOW(), '2007-12-03 18:19:26');
INSERT INTO `files` VALUES (1850109,'', '', 'broadcast/broad/002/032/181/932123456.jpeg', 'image/jpeg', NOW(), '2007-12-03 18:19:26');

INSERT INTO `user` VALUES (1,'jdgojfibicdcgngolgdeneiikginbonfxxxx','violet22','mpuyfaucher@violet.net',1,16951,9,14532,0,0,'84653822',1,0,113827693421946,0,1,NULL,'0000FF','2005-11-28',0,30,1,'','');

INSERT INTO `object` VALUES (31, '00039D402EAA','violet22', 1 , 1110907546, 3, 1156867813, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0, 30);

INSERT INTO `object_preferences` VALUES (31, 1,1,1);

INSERT INTO `object_profile` VALUES (31, 'violet22', 1850108, 'private_description',null,null, NOW(), NOW());

