SET FOREIGN_KEY_CHECKS = 0;

delete from `user` where  user_id = '103' or user_id = '104';
delete from `annu` where annu_user = '103' or annu_user = '104';
delete from `music` where music_id > 0;
delete from `musicstyle` where musicstyle_id > 0;
delete from `files` where id > 0;

SET FOREIGN_KEY_CHECKS = 1;
