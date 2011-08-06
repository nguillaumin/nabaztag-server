-- TODO: The timestamp values are definitely wrong
DELETE FROM `scheduling_type`;

INSERT INTO `scheduling_type` VALUES (1, 'Daily', '2011-08-06 19:25:56');
INSERT INTO `scheduling_type` VALUES (2, 'DailyWithMedia', '2011-08-06 19:28:23');
INSERT INTO `scheduling_type` VALUES (3, 'DailyWithDuration', '2011-08-06 19:28:23');
INSERT INTO `scheduling_type` VALUES (4, 'Weekly', '2011-08-06 19:28:23');
INSERT INTO `scheduling_type` VALUES (5, 'RandomWithFrequency', '2011-08-06 19:28:23');
INSERT INTO `scheduling_type` VALUES (6, 'VoiceTrigger', '2011-08-06 19:28:23');
INSERT INTO `scheduling_type` VALUES (7, 'InteractionTrigger', '2011-08-06 19:28:23');
INSERT INTO `scheduling_type` VALUES (8, 'NewContent', '2011-08-06 19:28:23');
INSERT INTO `scheduling_type` VALUES (9, 'NewContentWithFrequency', '2011-08-06 19:28:23');
INSERT INTO `scheduling_type` VALUES (10, 'NewContentWithKeywordAndMedia', '2011-08-06 19:28:23');
INSERT INTO `scheduling_type` VALUES (11, 'Ambiant', '2011-08-06 19:16:18');
INSERT INTO `scheduling_type` VALUES (12, 'AmbiantWithKeyword', '2011-08-06 19:28:23');
INSERT INTO `scheduling_type` VALUES (13, 'Frequency', '2011-08-06 19:28:23');
