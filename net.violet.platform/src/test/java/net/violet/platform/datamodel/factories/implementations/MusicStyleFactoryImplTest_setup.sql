SET FOREIGN_KEY_CHECKS = 0;

delete from `user` where  user_id = '103' or user_id = '104';
delete from `annu` where annu_user = '103' or annu_user = '104';
delete from `music` where music_id > 0;
delete from `musicstyle` where musicstyle_id > 0;
delete from `files` where id > 0;

INSERT INTO `user` VALUES (103,  "pass","Felix", "email1", 1, -2,1,10165,1,1,'796',0,1,0,0,1,NULL,'FFAE23',NULL,1,30,1,'','');
INSERT INTO `annu` VALUES (103, 'M', '', 'Varsovie', 'PL',0, 1, '1980-05-12', 'Janek', 'Kowalski','',NULL,36, NOW());
INSERT INTO `user` VALUES (104,  "pass1","Whiskas", "email2", 1, -2,1,10165,1,1,'796',0,1,0,0,1,NULL,'FFAE23',NULL,1,30,1,'','');
INSERT INTO `annu` VALUES (104, 'M', '', 'Varsovie', 'PL',0, 1, '1980-05-12', 'Minou', 'Kowalsky','',NULL,36, NOW());

INSERT INTO `files` VALUES (1,'/home/agata/Desktop/sons/1','2008-07-25',null,8),
(2,'/home/agata/Desktop/sons/2','2008-07-25',null,8),
(3,'/home/agata/Desktop/sons/3','2008-07-25',null,2);

INSERT INTO `music` VALUES (1,1,'CLIN_BONJOUR LITTLE_WORDS 1',42,63,1,63,1),
(2,1,'MP3_PERSO LIBRARY2',42,29,1,2,1),
(3,1,'MP3_SIGNATURE NABSHARE3',42,4,0,0,1),
(4,1,'REVEIL LITTLE_WORDS4',42,37,1,1,1),
(5,1,'TTS_PERSO LITTLE_WORDS5',42,51,1,1,1),
(6,1,'CLIN_BONJOUR LITTLE_WORDS6',42,63,1,1,1),
(7,2,'CLIN_BONJOUR LITTLE_WORDS 7',42,63,1,1,1),
(8,2,'MP3_PERSO LIBRARY8',42,29,1,2,1),
(9,2,'MP3_SIGNATURE NABSHARE9',42,4,1,0,1),
(10,2,'REVEIL LITTLE_WORDS10',42,37,1,1,1),
(11,2,'TTS_PERSO LITTLE_WORDS11',42,51,1,1,1),
(12,2,'CLIN_BONJOUR LITTLE_WORDS12',42,63,1,1,1),
(13,3,'CLIN_BONJOUR LITTLE_WORDS 13',42,63,1,1,1),
(14,3,'MP3_PERSO LIBRARY14',42,29,1,2,1),
(15,3,'MP3_SIGNATURE NABSHARE15',42,4,0,0,1),
(16,3,'REVEIL LITTLE_WORDS16',42,37,1,1,1),
(17,3,'TTS_PERSO LITTLE_WORDS17',42,51,1,1,1),
(18,3,'CLIN_BONJOUR LITTLE_WORDS18',42,63,1,1,1);


INSERT INTO `musicstyle` VALUES (63,'clin bonjour',0),
(29,'mp3 perso',0),
(4,'mp3 signature',0),
(37,'reveil',0),
(51,'tts perso',0);


SET FOREIGN_KEY_CHECKS = 1;
