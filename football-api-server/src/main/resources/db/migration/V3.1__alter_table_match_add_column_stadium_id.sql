ALTER TABLE `match` ADD COLUMN stadium_id INT;

ALTER TABLE `match` ADD FOREIGN KEY(stadium_id) REFERENCES stadium(id);
