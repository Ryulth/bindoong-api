CREATE TABLE `post` (
    `post_id`               VARCHAR(50)     NOT NULL,
    `user_id`               VARCHAR(50)     NOT NULL,
    `image_url`             VARCHAR(512)    NOT NULL,
    `content`               TEXT            DEFAULT NULL,
    `location_id`           VARCHAR(50)     DEFAULT NULL,
    `created_date_time`     DATETIME        NOT NULL,
    `updated_date_time`     DATETIME        NOT NULL,
    PRIMARY KEY (`post_id`),
    INDEX `IDX_user_id` (`user_id`),
    INDEX `IDX_post_id_user_id` (`post_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;