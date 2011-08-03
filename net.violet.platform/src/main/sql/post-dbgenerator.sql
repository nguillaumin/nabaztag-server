-- This script corrects some tables created with the DBGenerator utility

-- feeds.last_modified is NULL by default
ALTER TABLE  `feeds` CHANGE  `last_modified`  `last_modified` TIMESTAMP NULL DEFAULT NULL;

-- There is no isDuplicated field on object, this is a side effect of
-- another table (ZTAMPS) having a valid isDuplicated field which is
-- created by DBGenerator
ALTER TABLE `object` DROP COLUMN `isDuplicated`;

-- tag_tmp_site.last_day should be big enougth to
-- hold a timestamp 
ALTER TABLE  `tag_tmp_site` CHANGE  `last_day`  `last_day` BIGINT NULL DEFAULT NULL;