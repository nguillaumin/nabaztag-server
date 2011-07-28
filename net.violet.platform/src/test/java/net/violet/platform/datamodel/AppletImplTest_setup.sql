SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO `applets` ( `id` , `author_id` , `name` , `description` , `version` , `language` , `player_version` , `path` , `source_file` )
VALUES ('1', '1', 'test', 'test', '1.0', 'fr', '1', 'no path', 'no source');

SET FOREIGN_KEY_CHECKS = 1;