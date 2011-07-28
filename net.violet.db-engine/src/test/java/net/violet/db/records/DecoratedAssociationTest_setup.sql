SET FOREIGN_KEY_CHECKS = 0;

delete from `object_has_read_content`;
delete from `object` where object_login like 'DecoratedAssociationTest%';
delete from annu;

delete from `files` where path like '%DecoratedAssociationTest%';

delete from `user` where user_pseudo like 'DecoratedAssociationTestUser%';
delete from `black`;

delete from files where id = 1;

INSERT INTO `files` ( `id` , `path2mp3` , `path2chor` , `path` , `mime_type` , `creation_date` , `bestBefore` )
	VALUES ('1', NULL , NULL , '', 'audio/mpeg',CURRENT_TIMESTAMP , NULL);
	
SET FOREIGN_KEY_CHECKS = 1;