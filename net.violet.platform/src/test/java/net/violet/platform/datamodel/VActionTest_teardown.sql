delete from `subscription` where id > 0;

delete from `application` where application_id > 0;

delete from `action` where id IN ('947', '1');

delete from `object` where object_id in (31, 57520);

delete from `user` where user_id in (195, 84887);
