USE warehouse;
INSERT INTO products (code, name, cost, price, stock) VALUES
(1, 'Producto 1', 13.44, 15, 125),
(2, 'Producto 2', 4.25, 6, 100),
(3, 'Producto 3', 25, 28, 75),
(4, 'Producto 4', 32.8, 38, 200),
(5, 'Producto 5', 44.1, 50, 250),
(6, 'Producto 6', 12.3, 15, 18),
(7, 'Producto 7', 3.1, 6, 75),
(8, 'Producto 8', 111.11, 125, 42),
(9, 'Producto 9', 365.87, 398, 12),
(10, 'Producto 10', 13.44, 15, 125);

INSERT INTO stores (code, name, address, supervised) VALUES
(1, 'Tienda 1', 'Direccion tienda 1', false),
(2, 'Tienda 2', 'Direccion tienda 2', true),
(3, 'Tienda 3', 'Direccion tienda 3', true);

INSERT INTO store_products (store_code, product_code, stock) VALUES
(1, 2, 9),
(1, 8, 12),
(1, 9, 1 ),
(1, 4, 14),
(1, 7, 0 ),
(1, 1, 20);

INSERT INTO store_products (store_code, product_code, stock) VALUES
(2, 1, 11),
(2, 3, 1 ),
(2, 4, 4 ),
(2, 5, 10),
(2, 10, 29);

INSERT INTO store_products (store_code, product_code, stock) VALUES
(3, 7, 50),
(3, 6, 15),
(3, 3, 14),
(3, 4, 21),
(3, 9, 17);

INSERT INTO admin_users (code, name, username, password) values
(1, 'Administrador 1', 'admin1', 'password1');

INSERT INTO store_users (code, name, username, password, email, store_code) VALUES
(2, 'Usuario 1 Tienda 1', 'tienda1-user1', 'passut1', 'utienda1@tiendas.com', 1),
(3, 'Usuario 2 Tienda 1', 'tienda1-user2', 'passu2t1', 'u2tienda1@tiendas.com', 1),
(4, 'Usuario 1 tienda 2', 'utienda2', 'passut2', 'utienda2@tiendas.com', 2),
(5, 'Usuario 1 tienda 3', 'utienda3', 'passut3', 'utienda3@tiendas.com', 3);

INSERT INTO supervisor_users (code, name, username, password, email) VALUES
(6, 'Supervisor 1', 'supervi1', 'passuper', 'supervisor1@tiendas.com');

INSERT INTO warehouse_users (code, name, username, password, email) VALUES
(7, 'Usuario bodegas 1', 'bodegas1', 'passbodegas1', 'bodegas1@tiendas.com'),
(8, 'Usuario bodegas 2', 'bodegas2', 'passbodegas2', 'bodegas2@tiendas.com');

INSERT INTO store_warehouse_users (warehouse_user_code, store_code) VALUES
(7, 1),
(7, 3),
(8, 2),
(8, 3);

# orders
INSERT INTO orders (id, created_date, status, store_code, store_user_code) VALUES
(1, '2023-02-04', 'COMPLETADO', 1, 2);

INSERT INTO order_details (order_id, product_code, quantity, cost) VALUES
(1, 1, 3, 40.32),
(1, 8, 2, 222.22),
(1, 7, 10, 31);

INSERT INTO orders (id, created_date, status, store_code, store_user_code) VALUES
(2, '2023-02-09', 'SOLICITADO', 1, 3);

INSERT INTO order_details (order_id, product_code, quantity, cost) VALUES
(2, 1, 3, 40.32),
(2, 9, 1, 365.87);

INSERT INTO orders (id, created_date, status, store_code, store_user_code) VALUES
(3, '2023-02-15', 'RECHAZADO', 2, 4);

INSERT INTO order_details (order_id, product_code, quantity, cost) VALUES
(3, 1, 3, 26.88),
(3, 3, 2, 50);

INSERT INTO orders (id, created_date, status, store_code, store_user_code) VALUES
(4, '2023-02-15', 'ENVIADO', 3, 5);

INSERT INTO order_details (order_id, product_code, quantity, cost) VALUES
(4, 7, 6, 18.6),
(4, 3, 3, 75),
(4, 9, 2, 731.74);

# orders

# shipments
INSERT INTO shipments (id, store_code, warehouse_user_code, order_id, departure_date, status) VALUES
(1, 3, 8, 4, '2023-02-19', 'DESPACHADO');

INSERT INTO shipment_details (shipment_id, product_code, quantity, cost) VALUES
(1, 7, 6, 18.6),
(1, 3, 3, 75),
(1, 9, 2, 731.74);

INSERT INTO shipments (id, store_code, warehouse_user_code, order_id, departure_date, received_date, status) VALUES
(2, 1, 7, 1, '2023-02-11', '2023-02-13', 'RECIBIDO');

INSERT INTO shipment_details (shipment_id, product_code, quantity, cost) VALUES
(2, 1, 3, 40.32),
(2, 8, 2, 222.22),
(2, 7, 10, 31);

# shipments

INSERT INTO incidents (id, created_date, status, store_code, user_code, solution, shipment_id) VALUES
(1, '2023-02-14', 'SOLUCIONADA', 1, 2, 'Devolver producto', 2);

INSERT INTO incident_details (incident_id, product_code, quantity, description) VALUES
(1, 1, 1, 'PRODUCTO DAÑADO');

INSERT INTO returnings (id, created_date, status, store_code, user_code, shipment_id) VALUES
(1, '2023-02-15', 'ACTIVA', 1, 2, 2);

INSERT INTO returnings_details (returning_id, product_code, quantity, description, shipment_id, cost) VALUES
(1, 1, 1, 'PRODUCTO DAÑADO', 2, 13.44);

