SET FOREIGN_KEY_CHECKS = 0;

delete from `user` where user_id = '84887';

delete from `user_alternate_email` where user_id = 84887;

SET FOREIGN_KEY_CHECKS = 1;