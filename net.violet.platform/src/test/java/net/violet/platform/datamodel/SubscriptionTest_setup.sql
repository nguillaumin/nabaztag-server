SET FOREIGN_KEY_CHECKS = 0;

-- Generic elements
INSERT INTO `user` VALUES (195,'hmdoacjpinkekbjkxxxx','lapinou','gilles.lebault@orange-ft.com',1,31,9,14532, 0, 0, '84653822',1, 0, 113827693421946, 0, 1, NULL, '0000FF','2005-11-28',0,30,1,'','');
INSERT INTO `user` VALUES (84887,'nobnbacjegdcgelixxx','private','lp@violet.net',1,57520,9,14532, 0, 0, '84653822',1, 0, 113827693421946, 0, 1, NULL, '0000FF','2005-11-28',0,30,1,'','');
INSERT INTO `user` VALUES (777,'nobnbacjegdcgelixxx','nabtest','gerard@violet.net',1,0,9,14532, 0, 0, '84653822',1, 0, 113827693421946, 0, 1, NULL, '0000FF','2005-11-28',0,30,1,'','');
INSERT INTO `user` VALUES (12345,'nobnbacjegdcgelixxx','nabtest2','gerard@violet1.net',1,0,9,54321, 0, 0, '84653822',1, 0, 113827693421946, 0, 1, NULL, '0000FF','2005-11-28',0,30,1,'','');
INSERT INTO `user` VALUES (123456,'nobnbacjegdcgelixxx','nabtest3','gerard@violet2.net',1,0,9,654321, 0, 0, '84653822',1, 0, 113827693421946, 0, 1, NULL, '0000FF','2005-11-28',0,30,1,'','');

INSERT INTO `object` VALUES (31, '00039D402EAA','lapinou', 195 , 1110907546, 4, 1156867813, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0, 30);
INSERT INTO `object` VALUES (57520, '0013d3849ab6','private', 84887, 1178903133, 4, 1178903133, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0, 30);
INSERT INTO `object` VALUES (16951, '00039D40299D','violet22', 777, 1136281428, 4, 1156763864, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0, 30);
INSERT INTO `object` VALUES (54321, '00039D402HYD','violet222', 12345, 1136281428, 4, 1156763864, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0, 30);
INSERT INTO `object` VALUES (654321, '10039D402HYD','violet223', 123456, 1136281428, 4, 1156763864, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0, 15);

INSERT INTO `application` (`application_id`, `user_id`, `application_name`, `application_creation_date`, `application_class`, `application_category_id`) VALUES (1119, 1, 'net.violet.weather', '2008-08-01 16:16:42', 'NATIVE', 1);
INSERT INTO `application` (`application_id`, `user_id`, `application_name`, `application_creation_date`, `application_class`, `application_category_id`) VALUES (757, 1, 'net.violet.rss.bbc_technology', '2008-08-01 16:00:07', 'NATIVE', 22);
--

-- elements to test the timely subscription
INSERT INTO `subscription` VALUES (1,1119,31, '{"key" : "value"}');
INSERT INTO `subscription` VALUES (2,1119,57520, '{"key" : "value"}');
INSERT INTO `subscription` VALUES (3,1119,16951, '{}');

INSERT INTO `subscription_scheduling` VALUES (1,1,1);
INSERT INTO `subscription_scheduling` VALUES (2,2,1);
INSERT INTO `subscription_scheduling` VALUES (3,3,1);

INSERT INTO `subscription_scheduling_settings` VALUES (1, 1, 'Monday', '10:00:00');
INSERT INTO `subscription_scheduling_settings` VALUES (2, 1, 'Tuesday', '10:00:00');
INSERT INTO `subscription_scheduling_settings` VALUES (3, 1, 'Wednesday', '10:00:00');
INSERT INTO `subscription_scheduling_settings` VALUES (4, 1, 'Thursday', '10:00:00');
INSERT INTO `subscription_scheduling_settings` VALUES (5, 1, 'Friday', '10:00:00');
INSERT INTO `subscription_scheduling_settings` VALUES (6, 1, 'Saturday', '10:00:00');
INSERT INTO `subscription_scheduling_settings` VALUES (7, 1, 'Sunday', '10:00:00');

INSERT INTO `subscription_scheduling_settings` VALUES (8, 2, 'Monday', '10:15:00');
INSERT INTO `subscription_scheduling_settings` VALUES (9, 2, 'Tuesday', '10:15:00');
INSERT INTO `subscription_scheduling_settings` VALUES (10, 2, 'Wednesday', '10:15:00');
INSERT INTO `subscription_scheduling_settings` VALUES (11, 2, 'Thursday', '10:15:00');
INSERT INTO `subscription_scheduling_settings` VALUES (12, 2, 'Friday', '10:15:00');
INSERT INTO `subscription_scheduling_settings` VALUES (13, 2, 'Saturday', '10:15:00');
INSERT INTO `subscription_scheduling_settings` VALUES (14, 2, 'Sunday', '10:15:00');

INSERT INTO `subscription_scheduling_settings` VALUES (15, 3, 'Monday', '10:30:00');
INSERT INTO `subscription_scheduling_settings` VALUES (16, 3, 'Tuesday', '10:30:00');
INSERT INTO `subscription_scheduling_settings` VALUES (17, 3, 'Wednesday', '10:30:00');
INSERT INTO `subscription_scheduling_settings` VALUES (18, 3, 'Thursday', '10:30:00');
INSERT INTO `subscription_scheduling_settings` VALUES (19, 3, 'Friday', '10:30:00');
INSERT INTO `subscription_scheduling_settings` VALUES (20, 3, 'Saturday', '10:30:00');
INSERT INTO `subscription_scheduling_settings` VALUES (21, 3, 'Sunday', '10:30:00');

-- settings elements to test the walkByApplicationIdWithDistinctSettingValue method
INSERT INTO `subscription_settings` VALUES (1, 1, 'key', 'value');
INSERT INTO `subscription_settings` VALUES (2, 2, 'key', 'value');
INSERT INTO `subscription_settings` VALUES (3, 3, 'key', 'value2');

-- elements to test the FrequencySubscriptionByTimezone walker
INSERT INTO `subscription` VALUES (4,757,54321, '{}');
INSERT INTO `subscription` VALUES (5,757,654321, '{}');
INSERT INTO `subscription` VALUES (6,757,54321, '{}');
INSERT INTO `subscription` VALUES (7,757,654321, '{}');

INSERT INTO `subscription_scheduling` VALUES (4,4,13);
INSERT INTO `subscription_scheduling` VALUES (5,5,13);
INSERT INTO `subscription_scheduling` VALUES (6,6,9);
INSERT INTO `subscription_scheduling` VALUES (7,7,5);

INSERT INTO `subscription_scheduling_settings` VALUES (22, 4, 'Frequency', 'Hourly');
INSERT INTO `subscription_scheduling_settings` VALUES (23, 4, 'Last_time', '2008-08-29 11:00:00');
INSERT INTO `subscription_scheduling_settings` VALUES (24, 5, 'Frequency', 'Hourly');
INSERT INTO `subscription_scheduling_settings` VALUES (25, 5, 'Last_time', '2008-08-29 05:59:00');
INSERT INTO `subscription_scheduling_settings` VALUES (26, 6, 'Frequency', 'Often');
INSERT INTO `subscription_scheduling_settings` VALUES (27, 6, 'Last_time', '2008-08-29 11:20:00');
INSERT INTO `subscription_scheduling_settings` VALUES (28, 7, 'Frequency', 'Sometimes');
INSERT INTO `subscription_scheduling_settings` VALUES (29, 7, 'Last_time', '2008-08-29 04:30:00');

-- elements to test the NewContentWithKeyWordAndMedia walker
INSERT INTO `subscription` VALUES (8,1119,31, '{}');
INSERT INTO `subscription` VALUES (9,1119,57520, '{}');
INSERT INTO `subscription` VALUES (10,1119,16951, '{}');

INSERT INTO `subscription_scheduling` VALUES (8,8,10);
INSERT INTO `subscription_scheduling` VALUES (9,9,10);
INSERT INTO `subscription_scheduling` VALUES (10,10,10);

INSERT INTO `subscription_scheduling_settings` VALUES (30, 8, 'new_content.flag', '1');
INSERT INTO `subscription_scheduling_settings` VALUES (31, 9, 'new_content.flag', '1');
INSERT INTO `subscription_scheduling_settings` VALUES (32, 10, 'new_content.flag', '0');
--

-- 

SET FOREIGN_KEY_CHECKS = 1;
