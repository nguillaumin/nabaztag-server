SET FOREIGN_KEY_CHECKS=0;

INSERT INTO `feeds` VALUES (1,'http://www.google.com/feed', 1, NULL, NULL, 'RSS', 'FREE');
INSERT INTO `feeds` VALUES (2,'http://www.google.com/feed2', 1, NULL, NULL, 'RSS', 'FREE');
INSERT INTO `feeds` VALUES (3,'http://www.google.com/feed', 1, NULL, NULL, 'PODCAST', 'FULL');
INSERT INTO `feeds` VALUES (4,'http://www.google.com/feed2', 1, NULL, NULL, 'PODCAST', 'FREE');

SET FOREIGN_KEY_CHECKS=1;