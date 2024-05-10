CREATE TABLE my_books (
                          id VARCHAR(255) PRIMARY KEY,
                          current_user_id INT,
                          book_id INT, -- Новый столбец для хранения идентификатора книги
                          name VARCHAR(255),
                          author VARCHAR(255),
                          price INT,
                          quantity INT,
                          timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (current_user_id) REFERENCES person(id),
                          FOREIGN KEY (book_id) REFERENCES book(id) -- Внешний ключ, связывающий book_id с идентификатором книги в таблице book
);
