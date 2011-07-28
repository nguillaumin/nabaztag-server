SET FOREIGN_KEY_CHECKS = 0;
delete from message;
delete from files where path LIKE "%testMessage%";
delete from messenger where name = "private2";
delete from messenger where name = "kowalsky";
delete from message_sent;
delete from message_received;
delete from message_counter;
delete from user where user_id in(97238,90481);
delete from object where object_id in(63644,60463);

SET FOREIGN_KEY_CHECKS = 1;