SET FOREIGN_KEY_CHECKS = 0;
delete from message;
delete from files where path LIKE "%testMessage%";
SET FOREIGN_KEY_CHECKS = 1;