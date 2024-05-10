-- Добавляем нового пользователя с ролью "админ"
INSERT INTO person (username, password, email, role)
VALUES ('adminUser', 'hashedPassword123', 'admin@example.com', 'admin');
