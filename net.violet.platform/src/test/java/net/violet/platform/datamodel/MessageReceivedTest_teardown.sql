SET FOREIGN_KEY_CHECKS = 0;
delete from message;
delete from files where path LIKE "%testMessage%";
delete from messenger where name = "private";
delete from messenger where name = "kowalsky";
delete from message_sent;
delete from message_received;
SET FOREIGN_KEY_CHECKS = 1;