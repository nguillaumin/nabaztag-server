SET FOREIGN_KEY_CHECKS=0;

INSERT INTO `feeds` VALUES (1,'http://www.google.com/feed', 1, NULL, NULL, 'RSS', 'FREE');

INSERT INTO `files` VALUES (1, 'path1', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO `files` VALUES (2, 'path2', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO `files` VALUES (3, 'path3', CURRENT_TIMESTAMP, NULL, 1);

INSERT INTO `feeds` VALUES (2,'http://www.google.com/feed2', 1, NULL, NULL, 'RSS', 'FREE');

INSERT INTO `files` VALUES (4, 'path4', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO `files` VALUES (5, 'path5', CURRENT_TIMESTAMP, NULL, 1);
INSERT INTO `files` VALUES (6, 'path6', CURRENT_TIMESTAMP, NULL, 1);

INSERT INTO `feed_items` VALUES (1,2, '[4]','title1', 'link1', 'uri1');
INSERT INTO `feed_items` VALUES (4,2, '[4,5,6]','title4', 'link1', 'uri1');
INSERT INTO `feed_items` VALUES (2,2, '[4,6]','title2', 'link1', 'uri1');
INSERT INTO `feed_items` VALUES (3,2, '[5]','title3', 'link1', 'uri1');

SET FOREIGN_KEY_CHECKS=1;