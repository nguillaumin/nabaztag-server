SET FOREIGN_KEY_CHECKS = 0;
delete from `userLang` where user_id = '103' or user_id = '104' ;
delete from `object` where object_id = '16953' or object_id = '16952';
delete from `object_preferences` where object_id = '16953' or object_id = '16952';
delete from `user` where  user_id = '103' or user_id = '104';
delete from `annu` where annu_user = '103' or annu_user = '104';
SET FOREIGN_KEY_CHECKS = 1;