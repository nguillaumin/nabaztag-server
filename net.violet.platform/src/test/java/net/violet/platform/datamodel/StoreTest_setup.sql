SET FOREIGN_KEY_CHECKS = 0;

delete from `store` where id > 0;
delete from `store_city` where id > 0;
delete from `pays` where pays_id > 0;
delete from `continent` where id > 0;
delete from `files` where id = 1;

INSERT INTO `continent` VALUES 
(1, 'Asia'),
(2, 'Europe'),
(3, 'Africa'),
(4, 'Oceania'),
(5, 'South America'),
(6, 'North America'),
(7, 'Antartica');

INSERT INTO `pays` VALUES
(77, 2, 'LOC_pays/FR', 'FR', 1),
(84, 2, 'LOC_pays/GR', 'GR', 2),
(109, 2, 'LOC_pays/IT', 'IT', 2),
(171, 2, 'LOC_pays/NL', 'NL', 2),
(210, 2, 'LOC_pays/CH', 'CH', 2);

INSERT INTO `store_city` VALUES 
(1, '', 77),
(2, 'Aartselaar', 24),
(3, 'Paris', 77),
(4, 'Amsterdam', 171),
(5, 'Anderlecht', 24),
(6, 'Antibes', 77);

INSERT INTO `files`  VALUES 
(1, 'mock/picture', '2008-11-24 09:27:20', NULL,14);

INSERT INTO `store`  VALUES 
(139, 'Exell Aartselaar', 'physical', 1, 'Boomsesteenweg 14', '2630', 2, 'normal', '', 10, NULL),
(233, 'Espace SFR - Alencon', 'physical', 1, '63 Grande rue', '61000', 3, 'normal', '', 10, NULL),
(67, 'Hanazuki', 'physical', 1, 'Vijzelstraat 87', '1017 HG', 4, 'normal', '', 10, NULL),
(164, '90 Square Meters', 'physical', 1, 'Levantplein 52', '1019 MB', 4, 'normal', '', 10, NULL);

INSERT INTO `store` VALUES
(123, 'Exell Anderlecht', 'physical',1, 'Sylvain Dupuislaan 310 / Boulevard Sylvain Dupuis', '1070', 5, 'normal', '', 10, NULL),
(248, 'Espace SFR - Antibes', 'physical', 1, '2015 Route de Grasse', '06600', 6, 'normal', '', 10, NULL),
(274, 'Fnac.com', 'online', 1, '', '', 1, 'network', 'http:/recherche.fnac.com/m51193/Violet', 2, NULL),
(813, 'FNAC', 'online', 1, '', '', 1, 'network', 'http:/www.fnac.be/fr/', 5, NULL),
(853, 'FNAC', 'online', 1, '', '', 1, 'network', 'http://www.fnac.es', 0, NULL),
(854, 'FNAC', 'online', 1, '', '', 1, 'network', 'http://www.fnac.es', 0, NULL);



SET FOREIGN_KEY_CHECKS = 1;