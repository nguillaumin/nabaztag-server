SET FOREIGN_KEY_CHECKS = 0;

delete from `user` where user_id = '1';

delete from `object` where object_id = '16951' or object_id = '17953' or object_id = '17954' or object_id = '17955';
delete from `object_preferences` where object_id = '16951' or object_id = '17953'  or object_id = '17954' or object_id = '17955';
delete from `messenger` where object_id = '16951' or user_id = '1';


delete from `music` where music_id ='14532';
delete from `music` where music_id ='14533';
delete from `music` where music_id ='14534';
delete from `music` where music_id ='14535';

delete from `userLang` where user_id = '1';

delete from `user` where user_id = '2';

delete from `user` where user_id = '84887';

delete from `user` where user_id = '26111';
delete from `user` where user_id = '26112';

delete from `annu` where annu_user = 1 or annu_user = 2 or annu_user = 84887 or annu_user = 26111;

delete from `tag_tmp_site` where serial = '00904BBDA49D';

delete from `contact`;

delete from `black`;

delete from `files` where id = 5609430;
delete from `files` where id = 5609431;

delete from `userFriendsAddress`;
delete from `userFriendsAddress` where userFriendsAddress_userId = 2;

delete from `nabcast` where nabcast_id = 110;

delete from `messenger`;

delete from `message_received`;
delete from `message_sent`;

delete from `message`;
