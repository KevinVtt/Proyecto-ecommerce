INSERT INTO usuario (nombre, email, password, dni, rol) VALUES
('Juan Perez', 'juan.perez@example.com', 'password123', 12345678, 'cliente'),
('Maria Gomez', 'maria.gomez@example.com', 'password456', 87654321, 'cliente');
INSERT INTO producto (nombre, precio, descripcion, tipo, cantidad) VALUES
('Laptop HP', 1200.50, 'Laptop de 15 pulgadas con 8GB RAM', 'Electrónica', 10),
('Smartphone Samsung', 800.00, 'Smartphone con 128GB de almacenamiento', 'Electrónica', 20),
('Camiseta Nike', 25.00, 'Camiseta deportiva de algodón', 'Ropa', 50);
INSERT INTO pedido (fecha, estado) VALUES
('2023-10-01 10:00:00', 'Pendiente'),
('2023-10-02 14:30:00', 'Enviado');
INSERT INTO item_producto (pedido_id, usuario_id) VALUES
(1, 1),  -- ItemProducto 1 asociado al Pedido 1 y Usuario 1
(2, 2);  -- ItemProducto 2 asociado al Pedido 2 y Usuario 2
INSERT INTO producto_cantidad (item_producto_id, producto_id, cantidad) VALUES
(1, 1, 2),  -- ProductoCantidad 1: 2 unidades del Producto 1 en ItemProducto 1
(1, 2, 1),  -- ProductoCantidad 2: 1 unidad del Producto 2 en ItemProducto 1
(2, 3, 3);  -- ProductoCantidad 3: 3 unidades del Producto 3 en ItemProducto 2