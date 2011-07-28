SET FOREIGN_KEY_CHECKS = 0;
delete from message;
delete from user WHERE user_id in (97238, 90481);
delete from object WHERE object_id in (63643, 60463);
delete from files where path LIKE "%testMessage%";
delete from messenger where name = "private";
delete from messenger where name = "kowalsky";
delete from message_sent;
SET FOREIGN_KEY_CHECKS = 1;