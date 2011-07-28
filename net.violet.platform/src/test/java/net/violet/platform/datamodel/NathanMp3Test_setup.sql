SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO `nathan_version` ( `version_id` , `version_author` , `version_description` , `version_date` , `version_status` , `version_shared` , `version_official` , `version_nb` , `version_isbn` ) VALUES ('1', '15200', 'la description de l''espace',CURRENT_TIMESTAMP , 'PENDING', '2', '4', '05', '124545464454');
delete from files where id = 1;
INSERT INTO `files` ( `id` , `path`  , `creation_date` , `bestBefore`, mime_type_id )
	VALUES ('1', '',CURRENT_TIMESTAMP , NULL,8);
SET FOREIGN_KEY_CHECKS = 1;