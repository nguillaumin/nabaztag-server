SET FOREIGN_KEY_CHECKS = 0;

delete from `user` where  user_id = '103' or user_id = '104' or user_id = '42';
delete from `annu` where annu_user = '103' or annu_user = '104' or annu_user = '42';
delete from `music` where music_id > 0;
delete from `musicstyle` where musicstyle_id > 0;
delete from `categ` where categ_id > 0;
delete from `files` where id > 0;

INSERT INTO `user` VALUES (42,  "pass42","Felix42", "email142", 1, -2,1,10165,1,1,'796',0,1,0,0,1,NULL,'FFAE23',NULL,1,30,1,'','');
INSERT INTO `annu` VALUES (42, 'F', '', 'Varsovie', 'PL',0, 1, '1980-05-12', 'Janina', 'Kowalska','',NULL,36, NOW());
INSERT INTO `user` VALUES (103,  "pass","Felix", "email1", 1, -2,1,10165,1,1,'796',0,1,0,0,1,NULL,'FFAE23',NULL,1,30,1,'','');
INSERT INTO `annu` VALUES (103, 'M', '', 'Varsovie', 'PL',0, 1, '1980-05-12', 'Janek', 'Kowalski','',NULL,36, NOW());
INSERT INTO `user` VALUES (104,  "pass1","Whiskas", "email2", 1, -2,1,10165,1,1,'796',0,1,0,0,1,NULL,'FFAE23',NULL,1,30,1,'','');
INSERT INTO `annu` VALUES (104, 'M', '', 'Varsovie', 'PL',0, 1, '1980-05-12', 'Minou', 'Kowalsky','',NULL,36, NOW());

INSERT INTO `files` VALUES (1,'broadcast/broad/002/032/181/932.mp3',  '2007-12-01 18:19:26', NULL,8),
	(2,'broadcast/broad/008/155/073.mp3',  '2007-12-02 18:23:52', NULL,8),
	(3,'broadcast/broad/008/155/073.mp3',   '2007-12-03 18:23:52', NULL,8),
	(4,'broadcast/broad/995/809/385.mp3',   '2007-12-04 18:56:35', NULL,14),
	(5,'broadcast/broad/995/809/386.mp3',  '2007-12-01 15:27:25', NULL,1);

INSERT INTO `music` VALUES (1,1,'Noireaude',103,5,0,1,1),
(2,2,'Rougeaude',103,5,0,1,1),
(3,3,'Jauneaude',103,5,0,5,1),
(4,4,'Marronade',104,5,0,6,1),
(5,1,'Roseade',104,5,0,4,1),
(6,1,'Orangeaude',104,5,0,6,1),
(7,1,'Signature 1',104,4,0,7,1),
(8,1,'Signature 2',104,4,0,7,0),
(9,1,'Signature 103',103,4,0,7,0),
(10,1,'Vertaude',104,5,0,5,1);

INSERT INTO `files` VALUES (11,'/home/agata/Desktop/sons/1','2008-07-25',null,8),
(12,'/home/agata/Desktop/sons/2','2008-07-25',null,8),
(13,'/home/agata/Desktop/sons/3','2008-07-25',null,2);

INSERT INTO `music` VALUES (11,11,'clin bonjour  little_words 1',42,63,63,1,1),
(12,11,'mp3 perso library 2',42,29,1,2,1),
(13,11,'mp3 signature nabshare 3',42,4,4,0,1),
(14,11,'reveil little words 4',42,37,1,1,20),
(15,11,'tts perso little words 5',42,51,1,1,1),
(16,11,'clin bonjour little words 6',42,63,63,1,1),
(17,12,'clin bonjour little words 7',42,63,63,1,1),
(18,12,'mp3 perso library 8',42,29,1,2,1),
(19,12,'mp3 signature nabshare 9',42,4,1,0,1),
(20,12,'reveil little words 10',42,37,1,1,20),
(21,12,'tts perso little words 11',42,51,1,1,1),
(22,12,'clin bonjour little words 12',42,63,63,1,1),
(23,13,'clin bonjour little words 13',42,63,63,1,1),
(24,13,'mp3 perso library 14',42,29,1,2,1),
(25,13,'mp3 signature nabshare 15',42,4,4,1,1),
(26,13,'reveil little words 16',42,37,1,1,1),
(27,13,'tts perso little words 17',42,51,1,1,1),
(28,13,'clin bonjour little words 18',42,63,63,1,1),
(29,12,'reveil little words 29',42,37,1,1,20),
(30,12,'reveil little words 30',42,37,1,1,20),
(31,12,'tts perso little words 11',42,51,1,1,20);


INSERT INTO `musicstyle` VALUES (63,'clin bonjour',0),
(29,'mp3 perso',0),
(4,'mp3 signature',0),
(37,'reveil',0),
(51,'tts perso',0);

INSERT INTO `categ` VALUES (4, 'mp3 signature'),
(63, 'clin bonjour');

SET FOREIGN_KEY_CHECKS = 1;
