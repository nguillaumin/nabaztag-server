SET FOREIGN_KEY_CHECKS = 0;

-- Generic elements
INSERT INTO `user` VALUES (195,'hmdoacjpinkekbjkxxxx','lapinou','gilles.lebault@orange-ft.com',1,31,9,14532, 0, 0, '84653822',1, 0, 113827693421946, 0, 1, NULL, '0000FF','2005-11-28',0,30,1,'','');

INSERT INTO `object` VALUES (31, '00039D402EAA','lapinou', 195 , 1110907546, 4, 1156867813, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0, 30);
INSERT INTO `object` VALUES (57520, '0013d3849ab6','private', 195, 1178903133, 4, 1178903133, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0, 30);

INSERT INTO `application` (`application_id`, `user_id`, `application_name`, `application_creation_date`, `application_class`, `application_category_id`) VALUES (1119, 1, 'net.violet.weather', '2008-08-01 16:16:42', 'NATIVE', 1);
--
-- elements to test the timely subscription
INSERT INTO `subscription` VALUES (1,1119,31, '{}');
INSERT INTO `subscription` VALUES (2,1119,31, '{}');
INSERT INTO `subscription` VALUES (3,1119,57520, '{}');

INSERT INTO `subscription_scheduling` VALUES (1,1,1);
INSERT INTO `subscription_scheduling` VALUES (2,1,7);
INSERT INTO `subscription_scheduling` VALUES (3,2,1);
INSERT INTO `subscription_scheduling` VALUES (4,3,1);

SET FOREIGN_KEY_CHECKS = 1;
