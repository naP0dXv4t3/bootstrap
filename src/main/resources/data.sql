


INSERT INTO users (first_name, last_name, age, username, email, password) VALUES ('admin', 'admin', 10, 'admin', 'admin@example.com', '$2a$12$g/dPfJtn6KrOurpG53nzIeqAQ9tx8Q/y8NXjq9T/fYZo7dEDphpzm');


INSERT INTO roles (id, role_name) VALUES (1, 'USER');
INSERT INTO roles (id, role_name) VALUES (2, 'ADMIN');

INSERT INTO `users_roles` (`USER_ID`, `ROLE_ID`) VALUES (1, 1),(1, 2);

