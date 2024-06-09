CREATE TABLE stations (
                         id serial PRIMARY KEY,
                         station VARCHAR(50) NOT NULL
);

CREATE TABLE orders (
                       id serial PRIMARY KEY,
                       user_id INT NOT NULL,
                       from_station_id INT NOT NULL,
                       to_station_id INT NOT NULL,
                       status INT NOT NULL,
                       created TIMESTAMP DEFAULT now(),
                       FOREIGN KEY (from_station_id) REFERENCES stations(id),
                       FOREIGN KEY (to_station_id) REFERENCES stations(id)
);
