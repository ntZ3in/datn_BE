use `datn`;
CREATE TABLE tbluser (
                         user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         user_name VARCHAR(255) UNIQUE,
                         phone_number VARCHAR(20),
                         role VARCHAR(50),
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tbllocation (
                             location_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             lat DOUBLE NOT NULL,
                             lng DOUBLE NOT NULL
);

CREATE TABLE tblstore (
                          store_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          store_name VARCHAR(255),
                          phone_number VARCHAR(20) NOT NULL,
                          introduce TEXT,
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          update_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                          user_id BIGINT UNIQUE,
                          CONSTRAINT fk_store_user FOREIGN KEY (user_id)
                              REFERENCES tbluser(user_id)
                              ON DELETE SET NULL
                              ON UPDATE CASCADE
);

CREATE TABLE tblproduct (
                            product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            license_plates VARCHAR(50),
                            description TEXT,
                            phone_number VARCHAR(20),
                            phone_number2 VARCHAR(20),
                            start_location_id BIGINT,
                            end_location_id bigint,
                            start_time TIME,
                            end_time TIME,
                            price DOUBLE,
                            name VARCHAR(255),
                            quantity_seat INT,
                            policy TEXT,
                            utilities TEXT,
                            type VARCHAR(100),
                            create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                            update_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                            status VARCHAR(50),
                            owner_name VARCHAR(255),
                            store_id BIGINT,
                            CONSTRAINT fk_product_store FOREIGN KEY (store_id)
                                REFERENCES tblstore(store_id)
                                ON DELETE SET NULL
                                ON UPDATE CASCADE,
                            CONSTRAINT fk_product_start_location FOREIGN KEY (start_location_id)
                                REFERENCES tbllocation(location_id)
                                ON DELETE SET NULL
                                ON UPDATE CASCADE,
                            CONSTRAINT fk_product_end_location FOREIGN KEY (end_location_id)
                                REFERENCES tbllocation(location_id)
                                ON DELETE SET NULL
                                ON UPDATE CASCADE
);

CREATE TABLE tblstop (
                         stop_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         stop_time TIME,
                         location_id BIGINT NOT NULL,
                         type varchar(50),
                         deleted BOOLEAN DEFAULT FALSE,
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                         product_id BIGINT,

                         CONSTRAINT fk_stop_product FOREIGN KEY (product_id)
                             REFERENCES tblproduct(product_id)
                             ON DELETE SET NULL
                             ON UPDATE CASCADE,

                         CONSTRAINT fk_stop_location FOREIGN KEY (location_id)
                             REFERENCES tbllocation(location_id)
                             ON DELETE CASCADE
                             ON UPDATE CASCADE
);


CREATE TABLE tblimage (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          image_url VARCHAR(255),
                          tblproduct_id BIGINT,
                          CONSTRAINT fk_tblimage_product FOREIGN KEY (tblproduct_id)
                              REFERENCES tblproduct(product_id)
                              ON DELETE SET NULL
                              ON UPDATE CASCADE
);

CREATE TABLE tblnotice (
                           notice_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           title VARCHAR(255),
                           content TEXT,
                           created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                           store_name VARCHAR(255),
                           status varchar(50) default 'Còn hiệu lực',
                           last_update DATETIME DEFAULT NULL,
                           product_id BIGINT,
                           CONSTRAINT fk_tblnotice_product FOREIGN KEY (product_id)
                               REFERENCES tblproduct(product_id)
                               ON DELETE SET NULL
                               ON UPDATE CASCADE
);

CREATE TABLE tblorder (
                          order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          pick_location_id BIGINT,
                          destination_location_id BIGINT,
                          pick_time DATETIME,
                          message VARCHAR(500),
                          quantity INT,
                          phone_number VARCHAR(20),
                          price DOUBLE,
                          total_price DOUBLE,
                          order_status VARCHAR(50),
                          last_update DATETIME DEFAULT NULL,
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          user_id BIGINT,
                          trip_id BIGINT,
                          CONSTRAINT fk_order_user FOREIGN KEY (user_id)
                              REFERENCES tbluser(user_id)
                              ON DELETE SET NULL
                              ON UPDATE CASCADE,
                          CONSTRAINT fk_order_trip FOREIGN KEY (trip_id)
                              REFERENCES tbltrip(trip_id)
                              ON DELETE SET NULL
                              ON UPDATE CASCADE,
                          CONSTRAINT fk_order_pick_location FOREIGN KEY (pick_location_id)
                              REFERENCES tbllocation(location_id)
                              ON DELETE SET NULL
                              ON UPDATE CASCADE,
                          CONSTRAINT fk_order_destination_location FOREIGN KEY (destination_location_id)
                              REFERENCES tbllocation(location_id)
                              ON DELETE SET NULL
                              ON UPDATE CASCADE
);

CREATE TABLE tbltrip (
                         trip_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         price DOUBLE,
                         travel_date DATE NOT NULL,
                         start_time TIME,
                         end_time TIME,
                         remain_seat INT,
                         status VARCHAR(50),
                         product_id BIGINT,
                         UNIQUE (product_id,travel_date),
                         CONSTRAINT fk_trip_product
                             FOREIGN KEY (product_id)
                                 REFERENCES tblproduct(product_id)
                                 ON DELETE CASCADE
);



-- ALTER TABLE tblproduct
--     DROP COLUMN start_address,
--     DROP COLUMN end_address;

-- ALTER TABLE tblproduct
--     ADD start_location_id BIGINT,
--     ADD end_location_id BIGINT,
--     ADD CONSTRAINT fk_product_start_location FOREIGN KEY (start_location_id)
--         REFERENCES tbllocation(location_id)
--         ON DELETE SET NULL
--         ON UPDATE CASCADE,
--     ADD CONSTRAINT fk_product_end_location FOREIGN KEY (end_location_id)
--         REFERENCES tbllocation(location_id)
--         ON DELETE SET NULL
--         ON UPDATE CASCADE;
-- ALTER TABLE tblorder
--     DROP COLUMN pick_up_address,
--     DROP COLUMN destination_address;

-- ALTER TABLE tblorder
--     ADD pick_location_id BIGINT,
--     ADD destination_location_id BIGINT,
--     ADD CONSTRAINT fk_order_pick_location FOREIGN KEY (pick_location_id)
--         REFERENCES tbllocation(location_id)
--         ON DELETE SET NULL
--         ON UPDATE CASCADE,
--     ADD CONSTRAINT fk_order_destination_location FOREIGN KEY (destination_location_id)
--         REFERENCES tbllocation(location_id)
--         ON DELETE SET NULL
--         ON UPDATE CASCADE;

-- ALTER TABLE tblstop
--     DROP COLUMN stop_address;
-- ALTER TABLE tblstop
--     ADD location_id  BIGINT,
--     ADD CONSTRAINT fk_stop_location FOREIGN KEY (location_id)
--         REFERENCES tbllocation(location_id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE;