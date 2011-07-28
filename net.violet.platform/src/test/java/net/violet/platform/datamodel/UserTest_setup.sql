SET FOREIGN_KEY_CHECKS = 0;

delete from `user` where user_id = '1';
delete from `object` where object_id = '16951' or object_id = '17953' or object_id = '17954' or object_id = '17955';
delete from `object_preferences` where object_id = '16951' or object_id = '17953'  or object_id = '17954' or object_id = '17955';
delete from `messenger` where object_id = '16951' or user_id = '1';
delete from `music` where music_id ='14532';
delete from `userLang` where user_id = '1';
delete from `user` where user_id = '2';
delete from `user` where user_id = '84887';
delete from `user` where user_id = '26111';
delete from `user` where user_id = '26112';
delete from `annu` where annu_user = 1 or annu_user = 2 or annu_user = 84887 or annu_user = 26111;
delete from `tag_tmp_site` where serial = '00904BBDA49D';
delete from `contact` where person_id = '1';
delete from `black` where black_user= '1';

INSERT INTO `object` VALUES (16951,'00039D40299D8080','violet22',1,1136281428,3,1156763864,0,3,14,0,2147483647,17,5 ,120,'unknow/unknow',0,0, 30);
INSERT INTO `object` VALUES (17953,'00639D40299D6781','tescior2', 2 ,1136281428,3,1156763864,0,3,14,0,2147483647,17,5,120,'unknow/unknow',0,0, 30);
INSERT INTO `object` VALUES (17954,'00639D40299D6782','tescior3', 84887 ,1136281428,3,1156763864,0,3,14,0,2147483647,17,5 ,120,'unknow/unknow',0,0, 30);
INSERT INTO `object` VALUES (17955,'00639D40299D6783','tescior4', 26111 ,1136281428,3,1156763864,0,3,14,0,2147483647,17,5,120,'unknow/unknow',0,0, 30);
INSERT INTO `object_preferences` VALUES (16951, 1,1,1);
INSERT INTO `object_preferences` VALUES (17953, 1,1,1);
INSERT INTO `object_preferences` VALUES (17954, 0,1,1);
INSERT INTO `object_preferences` VALUES (17955, 1,1,1);

INSERT INTO `music` VALUES (14532,3341331,'A acceuil 1',1,29,0,0,1);

INSERT INTO `userLang` VALUES (1,1),(1,2);

INSERT INTO `user` VALUES (1,'jdgojfibicdcgngolgdeneiikginbonfxxxx','violet22','mpuyfaucher@violet.net',1,16951,9,14532,0,0,'84653822',1,0,113827693421946,1212593040,1,NULL,'0000FF','2005-11-28',0,30,1,'','');
INSERT INTO `user` VALUES (2,'ellkbbbhgaoklebcxx','syl','sylvain.huet@ambermind.com',1,19880,1,10165,0,0,'795',0,1,0,0,0,NULL,'FF0000','2005-11-28',1,30,1,'','');
INSERT INTO `user` VALUES (84887,'nobnbacjegdcgelixxx','private','lp@violet.net',2,63643,1,14529,1,1,'6748273',0,1,1191538602,1191505059,0,'','FF00FF',NULL,1,30,1,'firstName','lastName');
INSERT INTO `user` VALUES (26111,'empkddhafapnfeehxx','Borgnola','benjamincornic@gmail.com',1,27263,3,10208,1,1,'26746652',0,1,1157059819,1152300983,0,NULL,'FF00FF',NULL,1,30,1,'','');

INSERT INTO `user` VALUES (26112,'empkddhafapnfeehxx','delete','delete@gmail.com',1,0,3,10208,1,1,'26746652',0,1,1157059819,1152300983,0,NULL,'FF00FF',NULL,1,30,1,'','');

INSERT INTO `annu` VALUES (1, 'H', '', 'Paris', 'FR',0, 1, '1980-05-12', 'FirstN1', 'LastN1', '',NULL,36,NOW());
INSERT INTO `annu` VALUES (2, 'H', '', 'Londres', 'UK',0, 1, '1982-05-12', 'FirstN2', 'LastN2', '',NULL,36,NOW());
INSERT INTO `annu` VALUES (84887, 'F', '', 'Paris', 'FR',0, 1, '1977-07-04', 'FirstN3', 'LastN3', '',NULL,37,NOW());
INSERT INTO `annu` VALUES (26111, 'F','', 'Stockholm', 'SE',0, 1, '1985-07-15', 'FirstN4', 'LastN4', '',NULL,36,NOW());



INSERT INTO `tag_tmp_site` VALUES ('00904BBDA49D','gb/Harrow Weald',1186149314434,3, '192.168.1.42');

INSERT INTO `contact` VALUES (NULL,1,2,'ACCEPTED','2007-03-15 20:55:07');

INSERT INTO `black` VALUES (1,2,''),(1,84887,'');

INSERT INTO `messenger` VALUES (null,16951,null, null,null,1,"violet22"),(null,null,1, null,null,1,"violet22");

INSERT INTO `files` VALUES (5609430, 'broadcast/broad/photo/001/689/113/230/5609430_B.jpg', NOW(), null,14),
	(5609431, 'broadcast/broad/photo/001/812/570/019/5609431_B.jpg',NOW(), null,14);

-- Insert pour le test de fusion entre les comptes

INSERT INTO `contact` VALUES (NULL,84887, 26111,'ACCEPTED','2007-03-15 20:55:07');
INSERT INTO `contact` VALUES (NULL,2, 1,'ACCEPTED','2007-03-15 20:55:07');
INSERT INTO `contact` VALUES (NULL,26111, 2,'ACCEPTED','2007-03-15 20:55:07');

INSERT INTO `black` VALUES (2, 26111, '');
INSERT INTO `black` VALUES (26111, 2, '');
INSERT INTO `black` VALUES (26111, 84887, '');

INSERT INTO `userFriendsAddress` VALUES (84887, 'tomodachi1@docomo.ne.jp');
INSERT INTO `userFriendsAddress` VALUES (84887, 'tomodachi2@docomo.ne.jp');
INSERT INTO `userFriendsAddress` VALUES (2, 'tomodachi1@mixijp');
INSERT INTO `userFriendsAddress` VALUES (2, 'tomodachi2@mixi.jp');
INSERT INTO `userFriendsAddress` VALUES (2, 'tomodachi3@mixi.jp');

INSERT INTO `music` VALUES (14533,3341331,'warawara',84887,29,0,0,1);
INSERT INTO `music` VALUES (14534,3341331,'warawara',2,29,0,0,1);
INSERT INTO `music` VALUES (14535,3341331,'warawara',2,29,0,0,1);

INSERT INTO `nabcast` VALUES (110,'Nihon no nabcast',6,'Kore ha ii Nihon no nabcast desu yo.',0,2,0,3,'',3147596,0,1,0,'Nihon_Nab_cast_') ;

INSERT INTO `message` VALUES (1,NULL,NULL,NULL,1850117,NULL,'shiren','2007-11-27 15:13:07',0,0,0);
INSERT INTO `message` VALUES (2,NULL,NULL,NULL,1850117,NULL,'shiren2','2007-11-27 15:13:07',0,0,0);
INSERT INTO `message` VALUES (3,NULL,NULL,NULL,1850117,NULL,'shiren3','2007-11-27 15:13:07',0,0,0);
INSERT INTO `message` VALUES (4,NULL,NULL,NULL,1850117,NULL,'shiren4','2007-11-27 15:13:07',0,0,0);
INSERT INTO `message` VALUES (5,NULL,NULL,NULL,1850117,NULL,'shiren5','2007-11-27 15:13:07',0,0,0);

INSERT INTO `messenger` (id, user_id, object_id, name, color) VALUES(1, 1, 16951, 'violet22', 1);
INSERT INTO `messenger` (id, user_id, object_id, name, color) VALUES(2, 2, 17953, 'syl', 1);
INSERT INTO `messenger` (id, user_id, object_id, name, color) VALUES(3, 84887, 17954, 'private', 1);
INSERT INTO `messenger` (id, user_id, object_id, name, color) VALUES(4, 26111, 27263, 'private', 1);

INSERT INTO `message_received` VALUES (1, 4, 2, 'INBOX');
INSERT INTO `message_received` VALUES (2, 4, 3, 'INBOX');
INSERT INTO `message_received` VALUES (3, 4, 2, 'INBOX');
INSERT INTO `message_received` VALUES (4, 4, 3, 'INBOX');
INSERT INTO `message_received` VALUES (5, 4, 2, 'INBOX');

INSERT INTO `message_sent` VALUES (1, 2, 4, 'SENT');
INSERT INTO `message_sent` VALUES (2, 3, 4, 'SENT');
INSERT INTO `message_sent` VALUES (4, 3, 4, 'SENT');
INSERT INTO `message_sent` VALUES (5, 3, 4, 'SENT');


