SET FOREIGN_KEY_CHECKS = 0;

delete from `object_preferences` where object_id=31;
delete from `object_profile` where object_id=31;
delete from `user` where user_id = '1';
delete from `object` where object_id = '31';
delete from files where id in(1850108,1850109);





