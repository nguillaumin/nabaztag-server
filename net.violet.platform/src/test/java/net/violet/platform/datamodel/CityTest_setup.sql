SET FOREIGN_KEY_CHECKS = 0;
delete from `pays` where pays_id > 0;
delete from `annu` where annu_user > 0;
delete from `user_pwd` where user_id > 0;
delete from `user` where user_id > 0;
delete from `continent` where id > 0;

INSERT INTO `continent` (`id`, `name`) VALUES 
(1, 'Asia'),
(2, 'Europe'),
(3, 'Africa'),
(4, 'Oceania'),
(5, 'South America'),
(6, 'North America'),
(7, 'Antartica');

INSERT INTO `pays` VALUES 
(42,1,'Canada','CA', 2),
(210,1,'Suisse','CH',1),
(318, 2, 'France', 'FR',1),
(325, 2, 'Germany', 'DE',5);


SET FOREIGN_KEY_CHECKS = 1;