SET FOREIGN_KEY_CHECKS = 0;

delete from `action` where id = 1;
delete from `content` where action_id = 1;

SET FOREIGN_KEY_CHECKS = 1;