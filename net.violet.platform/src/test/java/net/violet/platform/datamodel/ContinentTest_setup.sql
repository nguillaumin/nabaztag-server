SET FOREIGN_KEY_CHECKS = 0;
delete from `continent` where id > 0;

INSERT INTO `continent` (`id`, `name`) VALUES 
(1, 'Asia'),
(2, 'Europe'),
(3, 'Africa'),
(4, 'Oceania'),
(5, 'South America'),
(6, 'North America'),
(7, 'Antartica');

SET FOREIGN_KEY_CHECKS = 1;