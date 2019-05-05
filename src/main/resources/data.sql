INSERT INTO user (id, first_name, last_name, user_name, email, password_hash) VALUES
-- Hashed "password"
  (1, 'Admin', 'Admin', 'admin', 'admin@admin.com', '$2a$10$ZUw7TUg/cKEJw4XlSS/6Wu0Pp05yi5kUO3cBYs5ewInpKXOW/US6G'),
-- Hashed "password1"
  (2, 'User', 'User', 'user', 'user@user.com', '$2a$10$6IDH7YBMlz3B2W9GiHdEI.sm6tlVRDYGmA9eWzUDucYYnqQVvmR66');

INSERT INTO role (id, name, is_default) VALUES
  (1, 'USER', 1), (2, 'ADMIN', 0);

INSERT INTO user_roles (user_id, role_id) VALUES
  (1, 1), (1, 2), (2, 2);

