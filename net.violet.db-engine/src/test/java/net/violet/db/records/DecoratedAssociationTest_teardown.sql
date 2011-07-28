SET FOREIGN_KEY_CHECKS = 0;

delete from `object_has_read_content`;
delete from `object` where object_login like 'DecoratedAssociationTest%';
delete from annu;

delete from `files` where path like '%DecoratedAssociationTest%';

delete from `user` where user_pseudo like 'DecoratedAssociationTestUser%';
delete from `black`;

SET FOREIGN_KEY_CHECKS = 1;