SET FOREIGN_KEY_CHECKS = 0;

delete from message;
delete from files where path LIKE '%testMessage%';
delete from messenger where name = 'private2';
delete from messenger where name = 'kowalsky';
delete from message_sent;
delete from message_received;
delete from message_counter;
delete from user where user_id in(97238,90481);
delete from object where object_id in(63644,60463);


INSERT INTO files (id, path,mime_type_id) values (100, '/testMessage/',8);
INSERT INTO message  ( `id` , `body` , `text` , `timeOfDelivery` , `count` , `palette` , `color` ) VALUES (1, 100, 'testMessage 1', '2007-11-19 17:01:34.041', 1, 1, 1);
INSERT INTO message  ( `id` , `body` , `text` , `timeOfDelivery` , `count` , `palette` , `color` ) VALUES (2, 100, 'testMessage 2', '2007-11-20 17:01:34.041', 1, 1, 1);
INSERT INTO message  ( `id` , `body` , `text` , `timeOfDelivery` , `count` , `palette` , `color` ) VALUES (3, 100, 'testMessage 3', '2007-12-19 17:01:34.041', 2, 1, 1);
INSERT INTO message  ( `id` , `body` , `text` , `timeOfDelivery` , `count` , `palette` , `color` ) VALUES (4, 100, 'testMessage 4', '2006-11-19 17:01:34.041', 0, 1, 1);
INSERT INTO message  ( `id` , `body` , `text` , `timeOfDelivery` , `count` , `palette` , `color` ) VALUES (5, 100, 'testMessage 5', '2009-11-19 17:01:34.041', 0, 1, 1);
INSERT INTO message  ( `id` , `body` , `text` , `timeOfDelivery` , `count` , `palette` , `color` ) VALUES (6, 100, 'testMessage 6', '2006-11-19 17:01:34.041', 0, 1, 1);
INSERT INTO message  ( `id` , `body` , `text` , `timeOfDelivery` , `count` , `palette` , `color` ) VALUES (7, 100, 'testMessage 7', '2006-12-19 17:01:34.041', 0, 1, 1);

INSERT INTO `user` VALUES (97238,'nobnbacjegdcgelixxx','private2','usermessagetest1@violet.net',2,63644,0,0,1,1,'6748273',0,1,1191538602,1191505059,0,'','FF0000',NULL,1,30,1,'firstName','lastName');

INSERT INTO `object` VALUES (63644,'0013d3849ab6','private2',97238,1191505098,3,1202310861,0,0,0,0,0,0,1,120,'paris',0,0, 30);

INSERT INTO `user` VALUES (90481,'nobnbacjegdcgelixxx','kowalsky','usermessagetest2@violet.net',2,60463,18,10163,1,1,'46825749',0,1,0,1184689373,0,'coucou','FF0000',NULL,NULL,30,1,'firstName','lastName');
INSERT INTO `object` VALUES (60463,'0019db001073','kowalsky',90481,1184689373,3,1184689373,0,0,0,0,0,0,1,120,'fr/Les Landes',0,0, 30);

INSERT INTO messenger (id, user_id, object_id, name, color) VALUES(1, 90481, 60463, 'kowalsky',1);
INSERT INTO messenger (id, user_id, object_id, name, color) VALUES(2, 97238, 63644, 'private2',1);

INSERT INTO `message_received` VALUES (1,1,2,'INBOX');
INSERT INTO `message_received` VALUES (2,1,2,'INBOX');
INSERT INTO `message_received` VALUES (3,1,2,'ARCHIVED');
INSERT INTO `message_received` VALUES (5,2,2,'PENDING');
INSERT INTO `message_received` VALUES (6,1,2,'INBOX');
INSERT INTO `message_received` VALUES (7,1,2,'ARCHIVED');
INSERT INTO `message_sent` VALUES (5,2,2,'SENT');
INSERT INTO `message_sent` VALUES (4,2,2,'SENT');

SET FOREIGN_KEY_CHECKS = 1;