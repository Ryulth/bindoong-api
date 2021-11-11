CREATE TABLE `comment` (
    `comment_id`            VARCHAR(50)     NOT NULL,
    `user_id`               VARCHAR(50)     NOT NULL,
    `post_id`               VARCHAR(50)     NOT NULL,
    `content`               TEXT            NOT NULL,
    `created_date_time`     DATETIME        NOT NULL,
    `updated_date_time`     DATETIME        NOT NULL,
    PRIMARY KEY (`comment_id`),
    INDEX `IDX_user_id` (`user_id`),
    INDEX `IDX_post_id` (`post_id`),
    INDEX `IDX_comment_id_user_id` (`comment_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;