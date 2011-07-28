SET FOREIGN_KEY_CHECKS = 0;

delete from evseq where evseq_event in (select event_id from event where event_obj = 62713);
delete from event where event_obj = 62713;
DELETE FROM messenger WHERE id in (18, 19, 20);
DELETE FROM message_received WHERE message_id IN (SELECT id FROM message WHERE timeOfDelivery = '2007-11-28 16:15:23');
DELETE FROM message WHERE timeOfDelivery = '2007-11-28 16:15:23';
delete from `object` where object_id = 62713;
delete from `object` where object_id = 62714;
delete from `object` where object_id = 62715;

SET FOREIGN_KEY_CHECKS = 1;