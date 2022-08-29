CREATE TABLE stadium (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE stadium_info (
  id INT NOT NULL AUTO_INCREMENT,
  rental_fee INT,
  rental_shower_room BOOLEAN,
  rental_shoes BOOLEAN,
  rental_uniform BOOLEAN,
  stadium_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (stadium_id) REFERENCES stadium (id)
);