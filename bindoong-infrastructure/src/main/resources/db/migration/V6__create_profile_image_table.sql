CREATE TABLE `profile_image` (
    `profile_image_id`      VARCHAR(50)     NOT NULL,
    `user_id`               VARCHAR(50)     NOT NULL,
    `image_url`             VARCHAR(255)    NOT NULL,
    `thumbnail_image_url`   VARCHAR(255)    NOT NULL,
    `created_date_time`     DATETIME        NOT NULL,
    `updated_date_time`     DATETIME        NOT NULL,
    PRIMARY KEY (`profile_image_id`),
    INDEX `IDX_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;