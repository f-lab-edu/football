CREATE TABLE `channel` (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255)
);

CREATE TABLE `message` (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  type VARCHAR(255),
  sender VARCHAR(255),
  content VARCHAR(255),
  create_at DATETIME,
  channel_id INT,
  FOREIGN KEY (channel_id) REFERENCES `channel` (id)
);

CREATE TABLE `participant` (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  channel_id INT,
  user_id INT,
  FOREIGN KEY (channel_id) REFERENCES `channel` (id),
  FOREIGN KEY (user_id) REFERENCES `user` (id)
);