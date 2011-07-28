SET FOREIGN_KEY_CHECKS = 0;
delete from pays where pays_id > 0;
delete from continent where id > 0;
SET FOREIGN_KEY_CHECKS = 1;