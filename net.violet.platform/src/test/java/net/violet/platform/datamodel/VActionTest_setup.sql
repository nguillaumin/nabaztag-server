SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO `action` VALUES (1,1,'http://www.lesinrocks.com/xml/rss/podcast.xml',4,'FREE','tag:twitter.com,2008-01-25T18:55:33+00:00:http://twitter.com/rafigaro/statuses/641128432','\"f6895c139aa8c2b8c46ddad24ea32f30\"',NULL,0);
INSERT INTO `action` VALUES (947,1,'http://radiofrance-podcast.net/podcast/rss_18994.xml',1,'FULL','tag:twitter.com,2008-01-25T18:55:33+00:00:http://twitter.com/rafigaro/statuses/641128432','\"f6895c139aa8c2b8c46ddad24ea32f30\"',NULL,1);

INSERT INTO `application` (`application_id`, `user_id`, `application_name`, `application_creation_date`, `application_class`, `application_category_id`) VALUES (1, 1, 'net.violet.rssFull', '2008-06-11 12:37:46', 'WEBUI', 1);
INSERT INTO `application` (`application_id`, `user_id`, `application_name`, `application_creation_date`, `application_class`, `application_category_id`) VALUES (2, 1, 'net.violet.podcastFull', '2008-06-11 12:37:46', 'WEBUI', 1);

INSERT INTO `user` VALUES (195,'hmdoacjpinkekbjkxxxx','lapinou','gilles.lebault@orange-ft.com',1,31,9,14532, 0, 0, '84653822',1, 0, 113827693421946, 0, 1, NULL, '0000FF','2005-11-28',0,30,1,'','');
INSERT INTO `user` VALUES (84887,'nobnbacjegdcgelixxx','private','lp@violet.net',1,57520,9,14532, 0, 0, '84653822',1, 0, 113827693421946, 0, 1, NULL, '0000FF','2005-11-28',0,30,1,'','');

INSERT INTO `object` VALUES (31, '00039D402EAA','lapinou', 195 , 1110907546, 4, 1156867813, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0, 30);
INSERT INTO `object` VALUES (57520, '0013d3849ab6','private', 84887, 1178903133, 4, 1178903133, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0, 30);

INSERT INTO `subscription` VALUES (1,1,31, '{"actionId" : "1"}');

SET FOREIGN_KEY_CHECKS = 1;
