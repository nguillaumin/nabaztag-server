-- This script corrects some tables created with the DBGenerator utility

-- feeds.last_modified is NULL by default
ALTER TABLE  `feeds` CHANGE  `last_modified`  `last_modified` TIMESTAMP NULL DEFAULT NULL;

-- There is no isDuplicated field on object, this is a side effect of
-- another table (ZTAMPS) having a valid isDuplicated field which is
-- created by DBGenerator

ALTER TABLE `object` DROP COLUMN `isDuplicated`;
