CREATE TABLE `post` (
    `post_id`               VARCHAR(100)    NOT NULL,
    `user_id`               BIGINT(20)      NOT NULL,
    `image_url`             VARCHAR(512)    NOT NULL,
    `created_date_time`     DATETIME        NOT NULL,
    `updated_date_time`     DATETIME        NOT NULL,
    PRIMARY KEY (`post_id`),
    INDEX `IDX_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;