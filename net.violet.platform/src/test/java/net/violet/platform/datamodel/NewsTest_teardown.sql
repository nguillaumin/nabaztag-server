SET FOREIGN_KEY_CHECKS = 0;
delete from `news` where id > 0;
delete from `product` where id > 0;
SET FOREIGN_KEY_CHECKS = 1;

