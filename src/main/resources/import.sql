
INSERT INTO usuario (nombre, email, password, dni, rol) VALUES
('Juan Perez', 'juan.perez@example.com', 'password123', 12345678, 'cliente'),
('Maria Gomez', 'maria.gomez@example.com', 'password456', 87654321, 'admin');


INSERT INTO producto (nombre, precio, descripcion, tipo, cantidad) VALUES
('Pasta de dientes', 5.99, 'Pasta dental con fluor', 'Higiene', 100),
('Cepillo de dientes', 3.50, 'Cepillo de cerdas suaves', 'Higiene', 150),
('Jabon de bano', 2.99, 'Jabon de glicerina', 'Higiene', 200),
('Shampoo', 7.99, 'Shampoo para cabello seco', 'Cuidado personal', 120);

INSERT INTO item_producto (usuario_id) VALUES
(1),
(2);

INSERT INTO producto_cantidad (item_producto_id, producto_id, cantidad) VALUES
(1, 1, 2),
(1, 2, 1),
(2, 3, 3),
(2, 4, 2);

INSERT INTO pedido (item_producto_id, estado) VALUES
(1, 'Pendiente'),
(2, 'Enviado'); 