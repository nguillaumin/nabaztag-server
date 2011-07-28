SET FOREIGN_KEY_CHECKS=0;

INSERT INTO `user` VALUES (1,'jdgojfibicdcgngolgdeneiikginbonfxxxx','violet22','mpuyfaucher@violet.net',1,16951,9,14532,0,0,'84653822',1,0,113827693421946,1212593040,1,NULL,'0000FF','2005-11-28',0,30,1,'','');
INSERT INTO `object` VALUES (31, '00039D402EAA','lapinou', 1 , 1110907546, 4, 1156867813, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0, 30);

insert into application values (1016,1,'net.violet.rssfull','2008-09-05 19:38:01','NATIVE',0,1,1,1);
insert into application values (1017,1,'net.violet.podcastfull','2008-09-05 19:38:01','NATIVE',0,1,1,1);

INSERT INTO `files` VALUES (1850108,'broadcast/broad/002/032/181/932.mp3',   NOW(), '2007-12-03 18:19:26',8),
	(1850109,'broadcast/broad/008/155/073.mp3',  NOW(), '2007-12-03 18:23:52',8),
	(1850117,'broadcast/broad/995/809/385.mp3',   NOW(),'2007-12-03 18:56:35',8),
	(1850118,'broadcast/broad/001/119/266/174.mp3',   NOW(),'2007-12-03 18:57:12',8),
	(2257079,'broadcast/broad/690/145/795.mp3',   NOW(), '2007-12-03 19:10:51',8),
	(2257080,'broadcast/broad/813/602/584.mp3',   NOW(),'2007-12-03 19:12:03',8),
	(2286949,'broadcast/broad/001/115/009/609.mp3',  NOW(), '2007-12-07 13:31:06',8),
	(2286959,'broadcast/broad/001/115/009/629.mp3',  NOW(), '2007-12-07 13:31:06',8),
	(3386949,'broadcast/broad/001/115/009/619.mp3',  NOW(), '2007-12-07 13:31:06',8),
(2292206,'broadcast/broad/001/587/287/686.mp3',  NOW(), '2007-12-10 08:06:04',8);

INSERT INTO `message` VALUES (1,NULL,NULL,NULL,1850117,NULL,"test d'envoi",'2007-11-27 15:13:07',0,0,0);


INSERT INTO `music` VALUES (10160,2257079,'Noireaude',0,5,0,0,0);

INSERT INTO `config_files` VALUES (1,1,2257080,'1', '1');

INSERT INTO `action` VALUES (1245,1,'http://twitter.com/statuses/friends_timeline.atom',4,'FREE','tag:twitter.com,2008-01-25T18:55:33+00:00:http://twitter.com/rafigaro/statuses/641128432','\"f6895c139aa8c2b8c46ddad24ea32f30\"',NULL,0);

INSERT INTO `content` VALUES (NULL,1245,1850108,'new content','', '');

INSERT INTO `subscription` VALUES (1, 1016, 31, '{"fileId" : "3386949"}');

INSERT INTO `feed_items` VALUES (1,2, '[2286959]','title1', 'link1', 'uri1');

SET FOREIGN_KEY_CHECKS=1;