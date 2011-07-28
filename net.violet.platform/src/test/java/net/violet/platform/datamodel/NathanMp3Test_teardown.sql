SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM `nathan_version` WHERE version_id = 1;
DELETE FROM  `nathan_mp3`;
DELETE FROM `files` WHERE id = 1;

SET FOREIGN_KEY_CHECKS = 1;