SET FOREIGN_KEY_CHECKS = 0;
delete from `product` where id > 0;


INSERT INTO `product` (`id`, `name`) VALUES 
(1, 'Corporate'),
(2, 'Nabaztag'),
(3, 'Mirror'),
(4, 'Ztamp'),
(5, 'Nanoztag'),
(6, 'Daldal'),
(7, 'Bookz'),
(8, 'Earz');

SET FOREIGN_KEY_CHECKS = 1;