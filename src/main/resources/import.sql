INSERT INTO usuario (nombre, email, password, dni, rol) VALUES ('Juan Perez', 'juan.perez@example.com', 'password123', 12345678, 'cliente');
INSERT INTO usuario (nombre, email, password, dni, rol) VALUES ('Maria Gomez', 'maria.gomez@example.com', 'password456', 87654321, 'cliente');
INSERT INTO producto (nombre, precio, descripcion, tipo, cantidad) VALUES ('Laptop HP', 1200.50, 'Laptop de 15 pulgadas con 8GB RAM', 'Electrónica', 10);
INSERT INTO producto (nombre, precio, descripcion, tipo, cantidad) VALUES ('Smartphone Samsung', 800.00, 'Smartphone con 128GB de almacenamiento', 'Electrónica', 20);
INSERT INTO producto (nombre, precio, descripcion, tipo, cantidad) VALUES ('Camiseta Nike', 25.00, 'Camiseta deportiva de algodón', 'Ropa', 50);
INSERT INTO item_producto (usuario_id) VALUES (2);
INSERT INTO item_producto (usuario_id) VALUES (1);
INSERT INTO producto_cantidad (item_producto_id, producto_id, cantidad) VALUES (1, 1, 2);
INSERT INTO producto_cantidad (item_producto_id, producto_id, cantidad) VALUES (1, 2, 1);
INSERT INTO producto_cantidad (item_producto_id, producto_id, cantidad) VALUES (2, 3, 3); 