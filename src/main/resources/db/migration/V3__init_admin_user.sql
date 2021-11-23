INSERT INTO `roles` (`id`, `name`, `description`,
                     `created_date`, `created_by`, `last_modified_date`, `last_modified_by`)
VALUES (1, 'ADMIN', 'Permission to manage all users.', CURRENT_TIMESTAMP, -1, CURRENT_TIMESTAMP, -1);

INSERT INTO `users` (`id`, `email`, `password`, `first_name`, `last_name`, `enabled`,
                     `created_date`, `created_by`, `last_modified_date`, `last_modified_by`)
VALUES (1, 'admin@shopping.com', '$2a$10$hXTEAjh59M4JmJSGu8Bt3uQzg.6KCAh/8PvOogjUVaOhHdUr.V8Vu', 'Power', 'Admin', TRUE,
        CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1);

INSERT INTO `users_roles` (`role_id`, `user_id`)
VALUES (1, 1);