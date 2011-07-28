SET FOREIGN_KEY_CHECKS = 0;
delete from `store_city` where id > 0;
delete from `pays` where pays_id > 0;

INSERT INTO `pays`  VALUES 
(24, 2, 'LOC_pays/BE', 'BE', 1),
(77, 2, 'LOC_pays/FR', 'FR', 1),
(84, 2, 'LOC_pays/GR', 'GR',2),
(109, 2, 'LOC_pays/IT', 'IT',5),
(171, 2, 'LOC_pays/NL', 'NL',2),
(210, 2, 'LOC_pays/CH', 'CH',2);

INSERT INTO `store_city`  VALUES 
(1, '', 77),
(2, 'Aartselaar', 24),
(3, 'Paris', 77),
(4, 'Amsterdam', 171),
(5, 'Anderlecht', 24),
(6, 'Antibes', 77),
(7, 'Antwerpen', 24),
(8, 'ASTI', 109),
(9, 'Athens', 84),
(10, 'Zurich', 210);
SET FOREIGN_KEY_CHECKS = 1;