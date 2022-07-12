CREATE TABLE `match_member` (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  match_id INT,
  user_id INT,
  FOREIGN KEY (match_id) REFERENCES `match` (id),
  FOREIGN KEY (user_id) REFERENCES `user` (id)
);