SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO `user` VALUES (1,'hmdoacjpinkekbjkxxxx','lapinou','gilles.lebault@orange-ft.com',1,31,9,14532, 0, 0, '84653822',1, 0, 113827693421946, 0, 1, NULL, '0000FF','2005-11-28',0,30,1,'','');

INSERT INTO `object` VALUES (1, '00039D402EAA','lapinou', 195 , 1110907546, 3, 1156867813, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0,30);
INSERT INTO `object` VALUES (2, '0013d3849ab6','private', 84887, 1178903133, 4, 1178903133, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0,30);
INSERT INTO `object` VALUES (3, '00039D40299D','violet22', 777, 1136281428, 4, 1156763864, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0,30);
INSERT INTO `object` VALUES (4, '00039D40288D','violet23', 777, 1186281428, 5, 1156763864, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0,30);
INSERT INTO `object` VALUES (5, '00039D40288E','violet24', 778, 1186281428, 5, 1156763864, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0,30);

INSERT INTO `groups` VALUES (1, 'name1', 'description', 1, 1, 1, 1, 1);
INSERT INTO `groups` VALUES (2, 'name2', 'description', 1, 1, 1, 1, 1);
INSERT INTO `groups` VALUES (3, 'name3', 'description', 1, 1, 1, 1, 1);

INSERT INTO `group_has_members` VALUES (1,1);
INSERT INTO `group_has_members` VALUES (1,4);
INSERT INTO `group_has_members` VALUES (1,5);

INSERT INTO `group_has_members` VALUES (2,1);
INSERT INTO `group_has_members` VALUES (2,4);
INSERT INTO `group_has_members` VALUES (2,3);

INSERT INTO `group_has_members` VALUES (3,1);
INSERT INTO `group_has_members` VALUES (3,5);


SET FOREIGN_KEY_CHECKS = 1;