SET FOREIGN_KEY_CHECKS = 0;

delete from `press` where id > 0;
delete from `product` where id > 0;
delete from `files` where id = 1;

INSERT INTO `product` (`id`, `name`) VALUES 
(1, 'Corporate'),
(2, 'Nabaztag'),
(3, 'Mirror'),
(4, 'Ztamp'),
(5, 'Nanoztag'),
(6, 'Daldal'),
(7, 'Bookz'),
(8, 'Earz');

INSERT INTO `files`  VALUES 
(1, '', '2008-11-24 09:27:20', NULL,8);

INSERT INTO `press` VALUES (
NULL , '39', 'Title1', 'Abs1', 1, NULL , '1'
),(
NULL , '39', 'Title2', 'Abs2', 1, NULL , '1'
),(
NULL , '40', 'Title3', 'Abs3', 1, NULL , '2'
);


SET FOREIGN_KEY_CHECKS = 1;