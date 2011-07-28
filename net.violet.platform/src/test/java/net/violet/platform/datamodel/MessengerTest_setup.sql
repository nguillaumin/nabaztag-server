SET FOREIGN_KEY_CHECKS = 0;
DELETE FROM user where user_pseudo = 'private';
DELETE FROM user where user_pseudo = 'kowalsky';
DELETE FROM object where object_login = 'private';
DELETE FROM object where object_login = 'kowalsky';
DELETE FROM messenger where name LIKE 'private%';
DELETE FROM messenger where name LIKE 'kowalsky%';
DELETE FROM music where music_id = '10163';

INSERT INTO `user` VALUES (97238,'nobnbacjegdcgelixxx','private','lp@violet.net',2,63643,6,0,1,1,'6748273',0,1,1191538602,1191505059,0,'','FF0000',NULL,1,30,1,'firstName','lastName');
INSERT INTO `user` VALUES (90481,'nobnbacjegdcgelixxx','kowalsky','nicolas@violet.net',2,60463,18,10163,1,1,'46825749',0,1,0,1184689373,0,'coucou','FF0000',NULL,NULL,30,1,'firstName','lastName');

INSERT INTO `object` VALUES (63643,'0013d3849ab6','private',97238,1191505098,4,1193842802,0,0,0,0,0,0,0,120,'fr/Les Landes',0,0, 30);
INSERT INTO `object` VALUES (60463,'0019db001073','kowalsky',90481,1184689373,4,1184689373,0,0,0,0,0,0,0,120,'fr/Les Landes',0,0, 30);

INSERT INTO messenger (id, user_id, object_id, name, color) VALUES(1, NULL, 60463, 'kowalsky', 1);
INSERT INTO messenger (id, user_id, object_id, name, color) VALUES(2, 90481, NULL, 'kowalsky', 1);

INSERT INTO `music` VALUES (10163,3341331,'A acceuil 1',1,29,0,0,1);

SET FOREIGN_KEY_CHECKS = 1;