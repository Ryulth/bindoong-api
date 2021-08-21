CREATE TABLE `user` (
    `user_id`               VARCHAR(50)     NOT NULL,
    `nickname`              VARCHAR(255)    NOT NULL,
    `login_type`            VARCHAR(100)    NOT NULL,
    `roles`                 VARCHAR(100)    NOT NULL,
    `created_date_time`     DATETIME        NOT NULL,
    `updated_date_time`     DATETIME        NOT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `facebook_user` (
  `facebook_id`              VARCHAR(255)    NOT NULL,
  `user_id`                  VARCHAR(50)     NOT NULL,
  `last_access_token`        VARCHAR(255)    NOT NULL,
  PRIMARY KEY (`facebook_id`),
  FOREIGN KEY (`user_id`) REFERENCES user(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `kakao_user` (
  `kakao_id`                 VARCHAR(255)    NOT NULL,
  `user_id`                  VARCHAR(50)     NOT NULL,
  `last_access_token`        VARCHAR(255)    NOT NULL,
  PRIMARY KEY (`kakao_id`),
  FOREIGN KEY (`user_id`) REFERENCES user(`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;