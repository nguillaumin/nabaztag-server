SET FOREIGN_KEY_CHECKS = 0;
delete from `store` where id > 0;
delete from `store_city` where id > 0;
delete from `pays` where pays_id > 0;
delete from `continent` where id > 0;
delete from `files` where id = 1;
SET FOREIGN_KEY_CHECKS = 1;

