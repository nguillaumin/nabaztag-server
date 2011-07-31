-- Inserts a fake user and all related data needed for login

DELETE FROM `annu`;

INSERT INTO `annu`
  (`annu_user`, `annu_sexe`, `annu_cp`, `annu_city`, `annu_country`, `annu_city_id`, `annu_confirm`, `annu_datebirth`, `annu_prenom`, `annu_nom`, `description`, `picture_file_id`, `user_lang`, `annu_update_time`)
VALUES
  (1, 'M', NULL, NULL, NULL, 1, 1, NULL, NULL, NULL, NULL, NULL, 1, '2011-07-31 13:47:12');

DELETE FROM `city`;

INSERT INTO `city`
  (`city_id`, `city_country_code`, `city_name`)
 VALUES
  (1, 'XX', 'City');

DELETE FROM `object`;

INSERT INTO `object`
  (`object_id`, `object_serial`, `object_login`, `object_owner`, `object_creation`, `object_hardware`, `object_lastping`, `object_test`, `object_left`, `object_right`, `object_n1`, `object_n2`, `object_nbmsg`, `object_mode`, `object_delay`, `object_loc`, `object_state`, `object_bc_version`, `time_zone`)
  VALUES
(1, '0123456789ABC', 'rabbit', 1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, 0, 4, 300, NULL, 0, 10, 1);

DELETE FROM `user`;

-- user_password is used for vadmin
-- my-nabaztag uses the user_pwd table
INSERT INTO `user`
  (`user_id`, `user_password`, `user_pseudo`, `user_email`, `user_lang`, `user_main`, `user_color`, `user_music`, `user_authmsg`, `user_authmsgrcv`, `user_identnumnab`, `user_image`, `user_authmsgrcvme`, `user_extconnect`, `user_creationDate`, `user_good`, `user_comment`, `user_color_sign`, `user_show_date`, `user_newsletter`, `user_timezone`, `user_24h`, `user_firstName`, `user_lastName`)
  VALUES
(1, 'dmiodnipffcggiclxx', '', 'rabbit@localhost.com', 1, 1, 1, 1, 0, 0, 'X', 1, 0, 1, 1312082958, 1, NULL, NULL, NULL, 0, 1, 0, 'Rabbit', NULL);

-- INSERT INTO `userLang` (`user_id`, `lang_id`) VALUES (1, 1);
-- INSERT INTO `userprefs` (`userprefs_id`, `userprefs_layout`) VALUES (1, 'X');

DELETE FROM `user_pwd`;
-- Password is 'rabbit'
INSERT INTO `user_pwd`
  (`user_id`, `pseudo`, `pwd`)
VALUES
  (1, 'rabbit', 'a51e47f646375ab6bf5dd2c42d3e6181');
  
  
DELETE FROM `application`;

INSERT INTO `application`
  (`application_id`, `user_id`, `application_name`, `application_creation_date`, `application_class`, `application_interactive`, `application_category_id`, `application_visible`, `isRemovable`)
VALUES
  (1, 1, 'VAdmin', '2011-07-31 19:10:23', 'WEBUI', 0, 1, 0, 0);
  
DELETE FROM `application_credentials`;

INSERT INTO `application_credentials`
  (`public_key`, `private_key`, `digested_key`, `application_role`, `application_id`)
VALUES
  ('VAdmin', NULL, NULL, NULL, 1);
