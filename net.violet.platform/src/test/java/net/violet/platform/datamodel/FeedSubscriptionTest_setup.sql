SET FOREIGN_KEY_CHECKS=0;

INSERT INTO `user` VALUES (1,'nobnbacjegdcgelixxx','nabtest2','gerard@violet.net',1,0,9,14532, 0, 0, '84653822',1, 0, 113827693421946, 0, 1, NULL, '0000FF','2005-11-28',0,30,1,'','');
INSERT INTO `object` VALUES (1, '00039D402EAA','lapinou1', 1 , 1110907546, 3, 1156867813, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0,30);
INSERT INTO `object` VALUES (2, '00039D402EAB','lapinou2', 1 , 1110907546, 3, 1156867813, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0,30);
INSERT INTO `object` VALUES (3, '00039D402EAC','lapinou3', 1 , 1110907546, 3, 1156867813, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0,30);

INSERT INTO `feeds` VALUES (1,'http://www.google.com/feed', 1, NULL, NULL, 'RSS', 'FREE');
INSERT INTO `feeds` VALUES (2,'http://www.google.com/feed2', 1, NULL, NULL, 'RSS', 'FREE');
INSERT INTO `feeds` VALUES (3,'http://www.google.com/feed3', 1, NULL, NULL, 'RSS', 'FREE');

INSERT INTO `files` VALUES (4, 'path4', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO `files` VALUES (5, 'path5', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO `files` VALUES (6, 'path6', CURRENT_TIMESTAMP, NULL, 1);

INSERT INTO `feed_items` VALUES (1,1, '[4]','title1', 'link1', 'uri1');
INSERT INTO `feed_items` VALUES (4,1, '[4,5,6]','title4', 'link1', 'uri1');
INSERT INTO `feed_items` VALUES (2,1, '[4,6]','title2', 'link1', 'uri1');
INSERT INTO `feed_items` VALUES (3,1, '[5]','title3', 'link1', 'uri1');

INSERT INTO `feed_subscriptions` VALUES (1, 1,2,NULL);

INSERT INTO `feed_subscriptions` VALUES (2,1,3,NULL);
INSERT INTO `feed_subscriptions` VALUES (3,2,3,NULL);
INSERT INTO `feed_subscriptions` VALUES (4,3,3,NULL);

INSERT INTO `feed_subscriptions` VALUES (5,2,2,NULL);

SET FOREIGN_KEY_CHECKS=1;