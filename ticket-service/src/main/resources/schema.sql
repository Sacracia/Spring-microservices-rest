CREATE TABLE IF NOT EXISTS station_tbl (
 id INT AUTO_INCREMENT PRIMARY KEY,
 station VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS order_tbl (
   id INT AUTO_INCREMENT PRIMARY KEY,
   user_id INT NOT NULL,
   from_station_id INT NOT NULL,
   to_station_id INT NOT NULL,
   status INT NOT NULL,
   created TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

INSERT INTO station_tbl (station) VALUES
 ('Moscow'),
 ('Tokio'),
 ('Shanghai'),
 ('London'),
 ('Berlin')




