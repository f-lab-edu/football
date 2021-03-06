CREATE TABLE `match` (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  start_time DATETIME,
  finish_time DATETIME,
  min_number INT,
  max_number INT,
  rule VARCHAR(255),
  limit_shoes VARCHAR(255),
  limit_level VARCHAR(255),
  limit_gender VARCHAR(255)
);

CREATE TABLE match_manager (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  match_id INT,
  user_id INT,
  FOREIGN KEY (match_id) REFERENCES `match` (id),
  FOREIGN KEY (user_id) REFERENCES user (id)
);