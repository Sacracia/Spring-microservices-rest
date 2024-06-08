CREATE TABLE IF NOT EXISTS user_tbl (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nickname VARCHAR(50) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE IF NOT EXISTS session_tbl (
 id INT AUTO_INCREMENT PRIMARY KEY,
 user_id INT NOT NULL,
 token VARCHAR(255) NOT NULL,
 expires TIMESTAMP NOT NULL,
 FOREIGN KEY (user_id) REFERENCES user_tbl(id)
);

INSERT INTO user_tbl (nickname, email, password) VALUES
 ('Dan', 'Dan@gmail.com', 'DanPassword'),
 ('Kate', 'Kate@gmail.com', 'KatePassword'),
 ('Alex', 'Alex@gmail.com', 'AlexPassword');
