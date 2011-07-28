SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO `action` (`id`, `lang_id`, `url`, `service_id`, `access_right`, `id_last_news`, `etag`, `last_modified`, `application_id`) VALUES 
(1, 1, 'http://www.lesinrocks.com/xml/rss/podcast.xml', 1, 'FREE', NULL, '"99dec7-274f5-5e574640"', '2008-12-09', 2625);

INSERT INTO `content` (`id`, `action_id`, `file_id`, `title`, `link`, `id_xml`) VALUES 
(17973955, 1, 37480817, 'Les Inrocks Podcast vol. 103 ', 'http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_103.mp3', 'http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_103.mp3'),
(19700835, 1, 39867939, 'Les Inrocks Podcast vol. 105 ', 'http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_105.mp3', 'http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_105.mp3'),
(19820071, 1, 40047351, 'Les Inrocks Podcast vol. 106 ', 'http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_106.mp3', 'http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_106.mp3'),
(21582847, 1, 42362601, 'Les Inrocks Podcast vol. 107 ', 'http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_107.mp3', 'http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_107.mp3'),
(21603751, 1, 42391791, 'Les Inrocks Podcast vol. 107 ', 'http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_107.mp3', 'http://podcasts.lesinrocks.com/inrocks/podcast/jd/JD_THE_DJ-Podcast_107.mp3');


SET FOREIGN_KEY_CHECKS = 1;