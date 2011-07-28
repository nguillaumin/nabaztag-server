SET FOREIGN_KEY_CHECKS = 0;

delete from `news` where id > 0;
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

INSERT INTO `news` VALUES (
NULL , '39', 'Tables', 'ajout de Tables', NULL , NULL , 'wlasnie dorzucilismy nowe table do BD', 'wlasnie dorzucilismy nowe table do BD, kiedy nagle z komputerow zaczal wydobywxac sie gesty, pomaranczowy dym.',
CURRENT_TIMESTAMP , NULL , '1'),(
NULL , '39', 'Chaises', 'ajout de Chaises', NULL , NULL , 'intro/nowe_krzesla','body/nowe_krzesla',
CURRENT_TIMESTAMP , NULL , '1'), (
NULL , '40', 'titre/1', 'abstract/nowe_table', '1', '1', 'intro/nowe_table', 'body/nowe_table',
CURRENT_TIMESTAMP , NULL , '2');

SET FOREIGN_KEY_CHECKS = 1;