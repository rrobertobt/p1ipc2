DROP DATABASE IF EXISTS warehouse;
CREATE DATABASE warehouse;
USE warehouse;

CREATE TABLE warehouse_users (
  code INT NOT NULL,
  name VARCHAR(100) NOT NULL,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (code)
);

CREATE TABLE admin_users (
  code INT NOT NULL,
  name VARCHAR(100) NOT NULL,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(50) NOT NULL,
  PRIMARY KEY (code)
);

CREATE TABLE supervisor_users (
  code INT NOT NULL,
  name VARCHAR(100) NOT NULL,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (code)
);

CREATE TABLE products (
  code INT NOT NULL,
  name VARCHAR(100) NOT NULL,
  cost DECIMAL(6,2) NOT NULL,
  price DECIMAL(6,2) NOT NULL,
  stock INT NOT NULL,
  PRIMARY KEY (code)
);

CREATE TABLE stores (
  code INT NOT NULL,
  name VARCHAR(100) NOT NULL,
  address VARCHAR(200) NOT NULL,
  supervised BOOLEAN NOT NULL,
  warehouse_user_code INT NOT NULL,
  PRIMARY KEY (code),
  FOREIGN KEY (warehouse_user_code) REFERENCES warehouse_users (code)
);

CREATE TABLE store_users (
  code INT NOT NULL,
  name VARCHAR(100) NOT NULL,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  store_code INT NOT NULL,
  PRIMARY KEY (code),
  FOREIGN KEY (store_code) REFERENCES stores (code)
);

CREATE TABLE orders (
  id INT AUTO_INCREMENT NOT NULL,
  created_date DATETIME not null DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(100) NOT NULL,
  description VARCHAR(1000),
  store_code INT NOT NULL,
  store_user_code INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (store_code) REFERENCES stores (code),
  FOREIGN KEY (store_user_code) REFERENCES store_users (code)
);

CREATE TABLE order_details (
    id INT AUTO_INCREMENT NOT NULL,
    order_id INT NOT NULL,
    product_code INT NOT NULL,
    quantity INT NOT NULL,
    cost DECIMAL(6,2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_code) REFERENCES products (code)
);

CREATE TABLE incidents (
  id INT AUTO_INCREMENT NOT NULL,
  created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(100) NOT NULL,
  store_code INT NOT NULL,
  user_code INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (store_code) REFERENCES stores (code),
  FOREIGN KEY (user_code) REFERENCES store_users (code)
);

CREATE TABLE incident_details(
  incident_id INT NOT NULL,
  product_code INT NOT NULL,
  quantity INT NOT NULL,
  description VARCHAR(1000),
  PRIMARY KEY (incident_id),
  FOREIGN KEY (incident_id) REFERENCES incidents(id),
  FOREIGN KEY (product_code) REFERENCES products(code)
);

CREATE TABLE returnings (
  id INT AUTO_INCREMENT NOT NULL,
  created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(100) NOT NULL,
  store_code INT NOT NULL,
  user_code INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (store_code) REFERENCES stores (code),
  FOREIGN KEY (user_code) REFERENCES store_users (code)
);

CREATE TABLE returnings_details(
  returning_id INT NOT NULL,
  product_code INT NOT NULL,
  quantity INT NOT NULL,
  description VARCHAR(1000),
  PRIMARY KEY (returning_id),
  FOREIGN KEY (returning_id) REFERENCES returnings(id),
  FOREIGN KEY (product_code) REFERENCES products(code)
);

CREATE TABLE store_products(
  store_code INT NOT NULL,
  product_code INT NOT NULL,
  PRIMARY KEY (store_code, product_code),
  FOREIGN KEY (store_code) REFERENCES stores (code),
  FOREIGN KEY (product_code) REFERENCES products (code)
);

CREATE TABLE shipments(
  id INT AUTO_INCREMENT NOT NULL,
  store_code INT NOT NULL,
  warehouse_user_code INT NOT NULL,
  order_id INT NOT NULL,
  departure_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  received_date DATETIME,
  status VARCHAR(100) NOT NULL DEFAULT 'Despachado',
  PRIMARY KEY (id),
  FOREIGN KEY (store_code) REFERENCES stores (code),
  FOREIGN KEY (warehouse_user_code) REFERENCES warehouse_users (code),
  FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE shipment_details(
  id INT AUTO_INCREMENT NOT NULL,
  shipment_id INT NOT NULL,
  product_code INT NOT NULL,
  quantity INT NOT NULL,
  cost DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (shipment_id) REFERENCES shipments (id),
  FOREIGN KEY (product_code) REFERENCES products (code)
);
