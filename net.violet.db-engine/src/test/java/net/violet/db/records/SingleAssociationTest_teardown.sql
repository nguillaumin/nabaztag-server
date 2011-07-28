SET FOREIGN_KEY_CHECKS = 0;

delete from `user` where user_pseudo like 'SingleAssociationTest%';
delete from `lang` where lang_title like 'SingleAssociationTest%';


SET FOREIGN_KEY_CHECKS = 1;