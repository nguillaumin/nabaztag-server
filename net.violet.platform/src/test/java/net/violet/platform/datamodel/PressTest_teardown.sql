SET FOREIGN_KEY_CHECKS = 0;
delete from `press` where id > 0;
delete from `product` where id > 0;
delete from `files` where id = 1;
SET FOREIGN_KEY_CHECKS = 1;

