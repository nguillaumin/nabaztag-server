SET FOREIGN_KEY_CHECKS = 0;
delete from `pays` where pays_id > 0;
delete from `annu` where annu_user> 0;
delete from `user_pwd` where user_id > 0;
delete from `user` where user_id > 0;
delete from `continent` where id > 0;
SET FOREIGN_KEY_CHECKS = 1;

