CREATE TABLE `lottery` (
  `issue_no` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lottery_open` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `open_time` datetime DEFAULT NULL,
  `odd` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `big` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`issue_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci