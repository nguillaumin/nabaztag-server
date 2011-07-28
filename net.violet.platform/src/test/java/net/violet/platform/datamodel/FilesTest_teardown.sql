SET FOREIGN_KEY_CHECKS=0;

delete from user where user_id=1;
delete from object where object_id > 1;
delete from  application where user_id=1;
DELETE FROM files where id IN (1850108, 1850109, 1850117, 1850118, 2257079, 2257080, 2286949, 2292206);
DELETE FROM message WHERE id = 1;
DELETE FROM content WHERE action_id = 1245;
DELETE FROM action WHERE id = 1245;
DELETE FROM music WHERE music_id = 10160;
DELETE FROM config_files WHERE id = 1;
DELETE FROM files where path='/test/test/test.test';
DELETE FROM files where mime_type_id='8';
delete from subscription where id > 0;
truncate feed_items;
SET FOREIGN_KEY_CHECKS=1;