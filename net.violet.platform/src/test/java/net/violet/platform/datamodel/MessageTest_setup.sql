SET FOREIGN_KEY_CHECKS = 0;
INSERT INTO message  ( `id` , `body` , `text` , `timeOfDelivery` , `count` , `color` ) VALUES (1, 2, "testMessage", '2007-11-19 17:01:34.041', 0, 1);
INSERT INTO files (id, path,mime_type_id) values (2, "/testMessage/",8);
SET FOREIGN_KEY_CHECKS = 1;