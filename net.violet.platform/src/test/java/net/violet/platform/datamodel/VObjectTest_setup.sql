SET FOREIGN_KEY_CHECKS = 0;

delete from `subscriber` where subscriber_user = '16951';
delete from `nabcast` where nabcast_id in (389,374,107,266,172,424,184,259,1135,480,933,959,518,611,290,668,858);
DELETE FROM user WHERE user_id = 195 OR user_id = 84887 OR user_id=777 OR user_id=778;
DELETE FROM annu WHERE annu_user in (84887,195,777,778);
DELETE FROM object WHERE object_id in (31,57520,16951,16952,16953);
delete from `object_profile` where object_id in (31,57520,16951,16952,16953);
delete from `object_preferences` where object_id in (31,57520,16951,16952,16953);
delete from object where object_serial = 'aaaaaaaaaaaa';
delete from object where object_serial = 'aaaaaaaaaaab';
delete from object where object_serial = 'aaaaaaaaaaac';
delete from files where id =1850108;
delete from files where id =123123;
truncate object_profile;
truncate object_preferences;
update hardware set picture_file_id=NULL where id=4;

INSERT INTO `user` VALUES (195,'hmdoacjpinkekbjkxxxx','lapinou','gilles.lebault@orange-ft.com',1,31,9,14532, 0, 0, '84653822',1, 0, 113827693421946, 0, 1, NULL, '0000FF','2005-11-28',0,30,1,'','');
INSERT INTO `user` VALUES (84887,'nobnbacjegdcgelixxx','private','lp@violet.net',1,57520,9,14532, 0, 0, '84653822',1, 0, 113827693421946, 0, 1, NULL, '0000FF','2005-11-28',0,30,1,'','');
INSERT INTO `user` VALUES (777,'nobnbacjegdcgelixxx','nabtest','gerard@violet.net',1,0,9,14532, 0, 0, '84653822',1, 0, 113827693421946, 0, 1, NULL, '0000FF','2005-11-28',0,30,1,'','');
INSERT INTO `user` VALUES (778,'nobnbacjegdcgelixxx','nabtest2','gerard@violet.net',1,0,9,14532, 0, 0, '84653822',1, 0, 113827693421946, 0, 1, NULL, '0000FF','2005-11-28',0,30,1,'','');

INSERT INTO `annu` VALUES (84887,'F',' ','Paris','FR',0,1,'1991-02-04','firstName','lastName', '',NULL,36,NOW());
INSERT INTO `annu` VALUES (195,'F','92130','Issy les Moulineaux','FR',0,0,'1973-04-27',NULL,NULL,'',NULL,36, NOW());
INSERT INTO `annu` VALUES (778,'F','92130','Issy les Moulineaux','FR',0,0,'1973-04-27','myFirstName','myLastName','',NULL,36, NOW());
INSERT INTO `annu` VALUES (777, 'M', '', 'Londres', 'US',0, 1, '1981-05-12', 'toto', NULL, '',NULL,36,NOW());

INSERT INTO `object` VALUES (31, '00039D402EAA','lapinou', 195 , 1110907546, 3, 1156867813, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0,30);
INSERT INTO `object` VALUES (57520, '0013d3849ab6','private', 84887, 1178903133, 4, 1178903133, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0,30);
INSERT INTO `object` VALUES (16951, '00039D40299D','violet22', 777, 1136281428, 4, 1156763864, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0,30);
INSERT INTO `object` VALUES (16952, '00039D40288D','violet23', 777, 1186281428, 5, 1156763864, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0,30);
INSERT INTO `object` VALUES (16953, '00039D40288E','violet24', 778, 1186281428, 5, 1156763864, 0, 0, 0,0,0,0,0,120,'fr/Les Landes',0,0,30);
INSERT INTO `object_preferences` VALUES (31, 1,1,1);
INSERT INTO `object_preferences` VALUES (57520, 1,1,1);
INSERT INTO `object_preferences` VALUES (16951, 0,1,1);
INSERT INTO `object_preferences` VALUES (16952, 1,1,1);
INSERT INTO `object_preferences` VALUES (16953, 1,1,1);

INSERT INTO `object_profile` VALUES (57520, 'private', null, 'private_description',null,null, NOW(), NOW());
INSERT INTO `object_profile` VALUES (31, 'lapinou', null, 'private_description',null,null, NOW(), NOW());
INSERT INTO `object_profile` VALUES (16951, 'violet22', null, 'private_description',null,null, NOW(), NOW());
INSERT INTO `object_profile` VALUES (16952, 'violet23', null, 'private_description',null,null, NOW(), NOW());
INSERT INTO `object_profile` VALUES (16953, 'violet24', null, 'private_description',null,null, NOW(), NOW());

INSERT INTO `subscriber` VALUES (16951,389,-1,-1),(16951,374,-1,-1),(16951,107,-1,-1),(16951,266,-1,-1),(16951,172,20,10),(16951,424,-1,-1),(16951,184,-1,-1),(16951,259,-1,-1),(16951,1135,12,15),(16951,480,19,10),(16951,933,1,5),(16951,959,18,37),(16951,518,22,50),(16951,611,23,10),(16951,290,15,25),(16951,668,20,15),(16951,858,18,45);
INSERT INTO `nabcast` VALUES (107,'Russian Nab[cast]',6,'Retrouver chaque jour de la musique russe varier, aborder sous differents styles! Trouver toutes les chansons passer sur vos lapins de notre nabcast sur http://lapin.metorine.org . Envie de nouveaux?',0,637,0,3,'',3147596,0,1,0,'Russian_Nab_cast_'),(172,'Le lapin ventriloque',3,'De temps en temps, à l\'heure du diner, votre lapin imitera un autre animal. A vous de deviner duquel il s\'agit... Pour commenter : http://nabaztag.forumactif.fr/Services-f2/Nabcasts-V1-V2-f13/Le-lapin-ventriloque-t545.htm',0,6521,0,0,'',771687,3,1,0,'Le_lapin_ventriloque'),(184,'Movies&Games',6,'Envie de bouger votre popotin sur la musique de votre film ou jeu préféré ? \r\n<br><br>Alors c\'est parti mon kiki pour du bon son chaque semaine !',0,10912,1181571717,31,'',0,0,1,0,'Movies_Games'),(259,'Les oreilles tordues',5,'De temps en temps le soir à 19h45.\r\n<br>Les petites blagues du lapin malicieux.',1,12315,1181571719,31,'0',10180,9,1,0,'Les_oreilles_tordues'),(266,'yepcoast',3,'c\'\'est sur la Yepcoast que résonne les musiques et les sons les plus étranges !! Selon l\'\'humeurs, reggae, funk, trip-hop, pop, hip-hop, sons bizzars et meme des inconnus!!',1,11038,1182767404,31,'FF0000',1256092,9,1,0,'yepcoast'),(290,'Voulez-vous voyager avec moi ?',29,'Ah the language of Love ...a smart rabbit is a rabbit that knows its french. Every day i can teach you a sentence in french that we can repeat over and over... i bet you by the end of the day you\'\'\'\'\'\'\'\'ll know quite enough to impress even yourself !',1,15534,1181571722,31,'0',0,0,1,0,'Voulez-vous_voyager_avec_moi_'),(374,'100% Français',6,'Chaque semaine, laissez-vous tenter par \'100% Français\', le plus français des nabcasts. Retrouvez le pire comme le meilleur de la chanson française. C\'est à vous de juger...',1,15912,1181571727,31,'FF0000',786846,5,1,0,'100_Français'),(389,'Dancing Nabz',6,'Envie de frétiller des oreilles ??? Votre Nabaztag adore swinger... Alors soyez prêt à danser dans votre salon au rythme de mes humeurs :) (Rock indé, Popbubble Gum, Disco, Francofrancois ... ca dépend des jours !)',0,9666,0,4,'',1755155,3,1,0,'Dancing_Nabz'),(424,'The Nab Jokes',27,'Here are some of the jokes wonderfully done by me!',1,22226,1181571727,31,'0',0,0,1,0,'The_Nab_Jokes'),(480,'News Italiane in Italiano',31,'Le principali news Italiane trovate in Rete',1,31001,1181571728,31,'FFFF00',10162,3,1,0,'News_Italiane_in_Italiano'),(518,'Texto diario',91,'Aviso para considerar el Texto diario',0,32111,1181571727,31,'',1436384,12,5,0,'Texto_Diario'),(611,'super window rabbit',15,'contemporary art tastefull bites and coup de coeurs',0,2931,1181571733,31,'',2020350,5,2,0,'super_window_rabbit'),(668,'Dance!',28,'Don\'t forget to dance and laugh every day!',1,52655,1182767418,31,'',0,0,3,0,'Dance_'),(858,'Technology Updates',31,'This pod cast updates users on the latest gadgets and gizmo\'s.',1,51312,1181571736,31,'',10180,5,3,0,'Technology_Updates'),(933,'Buenas noches',38,'Dice buenas noches',1,60065,1181571737,31,'0000ff',10175,4,3,0,'Buenas_noches'),(959,'80\'s and 90\'s music',35,'80\'s and 90\'s music',1,61204,1181571737,31,'00ffff',10192,1,3,0,'80_s_and_90_s_music'),(1135,'jakofree',33,'series films',1,64592,1182767447,31,'',10172,11,1,0,'jakofree');
INSERT INTO `files` VALUES (1850108,'image.jpg', NOW(), '2007-12-03 18:19:26',14);
INSERT INTO `files` VALUES (123123,'image2.jpg', NOW(), '2007-12-03 18:19:26',14);
update hardware set picture_file_id='123123' where id=4;
SET FOREIGN_KEY_CHECKS = 1;

