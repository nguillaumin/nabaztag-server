-- This script generates association tables
-- It has been hand crafter by reading the 'STRUCTURE DES BASES DE DONNEES.doc' file.

DROP TABLE IF EXISTS `application_hardware`;
CREATE TABLE `application_hardware` (
  `application_id` INT NOT NULL ,
  `hardware_id` INT NOT NULL
);

DROP TABLE IF EXISTS `application_has_tag`;
CREATE TABLE `application_has_tag` (
  `application_id` INT NOT NULL ,
  `tag_id` INT NOT NULL
);

DROP TABLE IF EXISTS `group_has_members`;
CREATE TABLE `group_has_members` (
  `group_id` INT NOT NULL ,
  `object_id` INT NOT NULL
);

DROP TABLE IF EXISTS `hardware_supports_types`;
CREATE TABLE `hardware_supports_types` (
  `hardware_id` INT NOT NULL ,
  `mime_type_id` INT NOT NULL
);

DROP TABLE IF EXISTS `hint_has_context`;
CREATE TABLE `hint_has_context` (
  `hint_id` INT NOT NULL ,
  `context_id` INT NOT NULL
);

DROP TABLE IF EXISTS `nathan_tag_version`;
CREATE TABLE `nathan_tag_version` (
  `version_id` INT NOT NULL ,
  `tag_id` INT NOT NULL
);

-- Doesn't respect the naming convention :(
DROP TABLE IF EXISTS `userLang`;
CREATE TABLE `userLang` (
  `user_id` INT NOT NULL ,
  `lang_id` INT NOT NULL
);