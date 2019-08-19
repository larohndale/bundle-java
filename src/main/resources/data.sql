INSERT INTO settings (id, theme_name) VALUES
  (1, 'default'), (2, 'cosmic'), (3, 'cosmic'), (4, 'cosmic');

INSERT INTO user (id, first_name, last_name, login, email, password_hash, is_deleted, settings_id) VALUES
-- Hashed "password"
  (1, 'Admin', 'Admin', 'admin', 'admin@admin.com', '$2a$10$ZUw7TUg/cKEJw4XlSS/6Wu0Pp05yi5kUO3cBYs5ewInpKXOW/US6G', false, 1),
-- Hashed "password1"
  (2, 'User1', 'User1', 'user1', 'user1@user.com', '$2a$10$6IDH7YBMlz3B2W9GiHdEI.sm6tlVRDYGmA9eWzUDucYYnqQVvmR66', false, 2),
-- Hashed "password1"
  (3, 'User2', 'User2', 'user2', 'user2@user.com', '$2a$10$6IDH7YBMlz3B2W9GiHdEI.sm6tlVRDYGmA9eWzUDucYYnqQVvmR66', false, 3),
  -- Hashed "password1"
  (4, 'User3', 'User3', 'user3', 'user3@user.com', '$2a$10$6IDH7YBMlz3B2W9GiHdEI.sm6tlVRDYGmA9eWzUDucYYnqQVvmR66', false, 4);

INSERT INTO image (user_id, image) VALUES
-- Bytes of the default picture
(1, null),
(2, null),
(3, null),
(4, null);

INSERT INTO role (id, name, is_default) VALUES
  (1, 'USER', 1), (2, 'ADMIN', 0);

INSERT INTO user_roles (user_id, role_id) VALUES
  (1, 1), (1, 2), (2, 2), (3, 1), (4, 1);




