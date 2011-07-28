-- New TTS Language & TTS Voice UpDate
insert into lang values(44, 'Føroyskt (Faroese)', 1, 'fo-FO', 2);

TRUNCATE ttsvoice;

INSERT INTO `ttsvoice` VALUES
(NULL,'AU - Colleen', 28, 'Karen', 'AU-Colleen',1),
(NULL,'AU - Jon', 28, 'Lee', 'AU-Jon',0),
(NULL,'BE - Hendrik', 9, 'Ellen', 'BE-Hendrik',1),
(NULL,'BE - Minna', 36, 'justine22k', 'BE-Minna',1),
(NULL,'BE - Sofie', 9, 'sofie22k', 'BE-Sofie',0),
(NULL,'BR - Lygia', 21, 'Raquel', 'BR-Lygia',1),
(NULL,'CA - Antonine', 24, 'Julie', 'CA-Antonine',1),
(NULL,'CA - Felix', 24, 'Felix', 'CA-Felix',0),
(NULL,'CN - Pan', 27, 'Mei-Ling', 'CN-Pan',1),
(NULL,'CZ - Zdenech', 11, 'Zuzana', 'CZ-Zdenech',1),
(NULL,'DE - Heidi', 4, 'Beate', 'DE-Heidi',0),
(NULL,'DE - Otto', 4, 'klaus22k', 'DE-Otto',1),
(NULL,'DE - Sarah', 4, 'sarah22k', 'DE-Sarah',0),
(NULL,'DE - Steffi', 4, 'Steffi', 'DE-Steffi',0),
(NULL,'DE - Yannick', 4, 'Yannick', 'DE-Yannick',0),
(NULL,'DK - Karen', 12, 'Ida', 'DK-Karen',0),
(NULL,'DK - Kjeld', 12, 'mette22k', 'DK-Kjeld',0),
(NULL,'DK - Pia', 12, 'Nanna', 'DK-Pia',1),
(NULL,'EG - Nabil', 8, 'youssef22k', 'EG-Nabil',1),
(NULL,'EG - Nayla', 8, 'salma22k', 'EG-Nayla',0),
(NULL,'ES - Alfonsina', 5, 'maria22k', 'ES-Alfonsina',0),
(NULL,'ES - Baltasar', 5, 'Diego', 'ES-Baltasar',0),
(NULL,'ES - Bertrana', 22, 'Nuria', 'ES-Bertrana',1),
(NULL,'ES - Dunixe', 33, 'Arantxa', 'ES-Dunixe',1),
(NULL,'ES - Emilia', 5, 'Monica', 'ES-Emilia',0),
(NULL,'ES - Rosalía', 5, 'Isabel', 'ES-Rosalia',1),
(NULL,'FI - Linus', 14, 'matti22k', 'FI-Linus',1),
(NULL,'FI - Tarja', 14, 'sanna22k', 'FI-Tarja',0),
(NULL,'FO - Sjurdur', 44, 'sjurdur22k', 'FO-Sjurdur',1),
(NULL,'FR - Anastasie', 1, 'Virginie', 'FR-Anastasie',0),
(NULL,'FR - Archibald', 1, 'bruno22k', 'FR-Archibald',0),
(NULL,'FR - Gertrude', 1, 'claire22k', 'FR-Gertrude',1),
(NULL,'FR - Julie', 1, 'julie22k', 'FR-Julie',0),
(NULL,'FR - Maxence', 1, 'Sebastien', 'FR-Maxence',0),
(NULL,'FR - Philomène', 1, 'alice22k', 'FR-Philomene',0),
(NULL,'GR - Antonis', 7, 'Alexandros', 'GR-Antonis',1),
(NULL,'GR - Dimitris', 7, 'dimitris22k', 'GR-Dimitris',0),
(NULL,'IE - Órla', 29, 'Moira', 'IE-Orla',1),
(NULL,'IN - Nîmâ', 35, 'Lekha', 'IN-Nima',1),
(NULL,'IN - Sangeeta', 30, 'Sangeeta', 'IN-Sangeeta',1),
(NULL,'IS - Snorri', 15, 'snorri22k', 'IS-Snorri',1),
(NULL,'IT - Assunta', 6, 'Silvia', 'IT-Assunta',1),
(NULL,'IT - Carlo', 6, 'vittorio22k', 'IT-Carlo',0),
(NULL,'IT - Chiara', 6, 'chiara22k', 'IT-Chiara',0),
(NULL,'IT - Ugo', 6, 'Paolo', 'IT-Ugo',0),
(NULL,'JP - Tamura', 23, 'Kyoko', 'JP-Tamura',1),
(NULL,'KR - Choe', 34, 'Narae', 'KR-Choe',1),
(NULL,'MX - Guadalupe', 32, 'rosa22k', 'MX-Guadalupe',1),
(NULL,'NL - Femke', 13, 'femke22k', 'NL-Femke',1),
(NULL,'NL - Max', 13, 'max22k', 'NL-Max',0),
(NULL,'NL - Renate', 13, 'Claire', 'NL-Renate',0),
(NULL,'NO - Cora', 16, 'Stine', 'NO-Cora',1),
(NULL,'NO - Kari', 16, 'kari22k', 'NO-Kari',0),
(NULL,'NO - Sigrid', 16, 'Nora', 'NO-Sigrid',0),
(NULL,'NO -Arild', 16, 'olav22k', 'NO-Arild',0),
(NULL,'PL - Hanka', 20, 'ania22k', 'PL-Hanka',0),
(NULL,'PL - Ignacy', 20, 'Agata', 'PL-Ignacy',1),
(NULL,'PT - Celia', 10, 'celia22k', 'PT-Celia',1),
(NULL,'RU - Bella', 19, 'Katerina', 'RU-Bella',1),
(NULL,'RU - Maïa', 19, 'alyona22k', 'RU-Maia',0),
(NULL,'SE - Hjalmar', 17, 'erik22k', 'SE-Hjalmar',0),
(NULL,'SE - Liza', 17, 'elin22k', 'SE-Liza',0),
(NULL,'SE - Maj', 17, 'Alva', 'SE-Maj',0),
(NULL,'SE - Selma', 17, 'emma22k', 'SE-Selma',1),
(NULL,'TH - Boon-mee', 25, 'Narisa', 'TH-Boon-mee',1),
(NULL,'TR - Asli', 18, 'Aylin', 'TR-Asli',1),
(NULL,'TR - Sezen', 18, 'ipek22k', 'TR-Sezen',0),
(NULL,'UK - Edwin', 3, 'Daniel', 'UK-Edwin',0),
(NULL,'UK - Leonard', 3, 'peter22k', 'UK-Leonard',0),
(NULL,'UK - Mister muggles', 3, 'graham22k', 'UK-Mistermuggles',0),
(NULL,'UK - Penelope', 3, 'lucy22k', 'UK-Penelope',1),
(NULL,'UK - Rachel', 3, 'rachel22k', 'UK-Rachel',0),
(NULL,'UK - Shirley', 3, 'Serena', 'UK-Shirley',0),
(NULL,'US - Bethany ', 2, 'Samantha', 'US-Bethany',0),
(NULL,'US - Billye', 2, 'Jennifer', 'US-Billye',0),
(NULL,'US - Clarence', 2, 'Tom', 'US-Clarence',0),
(NULL,'US - Darleen  ', 2, 'Jill', 'US-Darleen',0),
(NULL,'US - Ernest', 2, 'ryan22k', 'US-Ernest',0),
(NULL,'US - Liberty', 2, 'Donna', 'US-Liberty',1),
(NULL,'US - Lilian', 2, 'heather22k', 'US-Lilian',0),
(NULL,'YUE- Bai bo', 26, 'Sin-Ji', 'YUE-Baibo',1),
(NULL,'ZA - Wilbur', 31, 'Tessa', 'ZA-Wilbur',1);


-- new feeds architecture
DROP TABLE IF EXISTS `feed_items`, `feeds`,`feed_subscriptions`;

CREATE TABLE `feeds` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `url` varchar(255) NOT NULL,
  `language_id` int(11) unsigned NOT NULL,
  `last_modified` timestamp NULL default NULL,
  `etag` varchar(255) default NULL,
  `type` enum('RSS','PODCAST','GROUP') NOT NULL,
  `access_right` enum('FREE','FULL','OFFLINE') NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `unique_by_url_and_type` (`url`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

CREATE TABLE `feed_items` (
`id` INT( 11 ) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY ,
`feed_id` INT( 11 ) UNSIGNED NOT NULL ,
`contents` VARCHAR( 255 ) NOT NULL DEFAULT '[]',
`title` TEXT NOT NULL ,
`link` TEXT NOT NULL ,
`uri` TEXT NOT NULL
) ENGINE = innodb CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `feed_items` ADD INDEX ( `feed_id` );

ALTER TABLE `feed_items`
  ADD CONSTRAINT `feed_items_ibfk_1` FOREIGN KEY (`feed_id`) REFERENCES `feeds` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
  
CREATE TABLE `feed_subscriptions` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `object_id` int(11) unsigned NOT NULL,
  `feed_id` int(11) unsigned NOT NULL,
  `last_read_item` int(11) unsigned default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `one_subscription_per_feed` (`object_id`,`feed_id`),
  KEY `feed_id` (`feed_id`),
  KEY `last_read_item` (`last_read_item`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

ALTER TABLE `feed_subscriptions`
  ADD CONSTRAINT `feed_subscriptions_ibfk_1` FOREIGN KEY (`object_id`) REFERENCES `object` (`object_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `feed_subscriptions_ibfk_4` FOREIGN KEY (`feed_id`) REFERENCES `feeds` (`id`);

-- drop no longer used table 'subscription_settings'
drop table `subscription_settings`;

-- change object_has_read_content.object_id foreign key's constraint in order to avoid errors for an object deleting processus.
ALTER TABLE object_has_read_content
ADD CONSTRAINT delete_content_with_object FOREIGN KEY (object_id)
REFERENCES object (object_id)
ON DELETE CASCADE; 


CREATE TABLE `notifications` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `sender_id` int(10) unsigned NOT NULL,
  `application_id` int(10) unsigned NOT NULL,
  `recipient_id` int(10) unsigned NOT NULL,
  `status` enum('PENDING','REJECTED','ACCEPTED','FINISHED', 'CANCELLED') NOT NULL default 'PENDING',
  `creation_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `unique_key` (`sender_id`,`application_id`,`recipient_id`,`status`),
  CONSTRAINT `sender_fk` FOREIGN KEY (`sender_id`) REFERENCES `object` (`object_id`),
  CONSTRAINT `recipient_fk` FOREIGN KEY (`recipient_id`) REFERENCES `object` (`object_id`),
  CONSTRAINT `application_fk` FOREIGN KEY (`application_id`) REFERENCES `application` (`application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Stats
DROP TABLE stats;
CREATE TABLE `stats_message` (
`id` INT( 11 ) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY ,
`user_id` INT( 11 ) unsigned NOT NULL ,
`object_id` INT( 11 ) unsigned NOT NULL ,
`time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`canal` ENUM( "API", "WEB" ) NOT NULL ,
`hardware_id` INT( 11 ) unsigned NOT NULL ,
INDEX ( `time` , `canal` , `hardware_id` )
) ENGINE = innodb CHARACTER SET utf8 COLLATE utf8_general_ci;

-- Paris, FR geocoding
update object_profile,annu,object set object_profile.object_longitudeGPS = 2.35098710000000000000, object_profile.object_latitudeGPS = 48.85666670000000000000 where object.object_id = object_profile.object_id AND object_profile.object_longitudeGPS IS NULL AND annu.annu_user = object.object_owner AND annu.annu_country = 'FR' AND annu.annu_city = 'Paris';
-- London, UK geocoding
update object_profile,annu,object set object_profile.object_longitudeGPS = -0.12623620000000000000, object_profile.object_latitudeGPS = 51.50015240000000000000 where object.object_id = object_profile.object_id AND object_profile.object_longitudeGPS IS NULL AND annu.annu_user = object.object_owner AND annu.annu_country = 'UK' AND annu.annu_city = 'London';

alter table object_has_read_content drop FOREIGN KEY  `object_has_read_content_ibfk_4`;

create temporary table subscription_ear select id,count(object_id) as c from subscription where application_id=2075 group by object_id having c > 1;
delete from subscription where application_id=2075 and id in (select id from subscription_ear);
-- check
select count(object_id) as c from subscription where application_id=2075 group by object_id having c > 1;

