SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM nathan_version WHERE version_author = 63643;
delete from user WHERE user_id in (97238, 90481);
delete from object WHERE object_id in (63643, 60463);


SET FOREIGN_KEY_CHECKS = 1;