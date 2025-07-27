-- Script para insertar datos de prueba de servicios
-- Ejecutar en la base de datos PostgreSQL

-- Insertar categorías si no existen
INSERT INTO categorias (id, nombre, descripcion) 
VALUES 
    ('cat1', 'Veterinaria', 'Servicios veterinarios básicos'),
    ('cat2', 'Estética', 'Servicios de belleza para mascotas'),
    ('cat3', 'Emergencias', 'Servicios de emergencia veterinaria')
ON CONFLICT (id) DO NOTHING;

-- Insertar servicios de prueba
INSERT INTO servicios (id, nombre, descripcion, precio, tipo, categoria_id) 
VALUES 
    ('serv1', 'Consulta Veterinaria', 'Consulta general con el veterinario', 25.00, 'CONSULTA', 'cat1'),
    ('serv2', 'Vacunación', 'Aplicación de vacunas básicas', 35.00, 'VACUNACION', 'cat1'),
    ('serv3', 'Baño y Corte', 'Servicio completo de baño y corte de pelo', 45.00, 'ESTETICA', 'cat2'),
    ('serv4', 'Desparasitación', 'Tratamiento antiparasitario', 30.00, 'TRATAMIENTO', 'cat1'),
    ('serv5', 'Emergencia Nocturna', 'Atención de emergencias 24/7', 80.00, 'EMERGENCIA', 'cat3'),
    ('serv6', 'Radiografía', 'Examen radiológico', 60.00, 'DIAGNOSTICO', 'cat1'),
    ('serv7', 'Cirugía Menor', 'Procedimientos quirúrgicos menores', 150.00, 'CIRUGIA', 'cat1'),
    ('serv8', 'Peluquería Premium', 'Servicio de peluquería con productos premium', 65.00, 'ESTETICA', 'cat2');

-- Verificar los datos insertados
SELECT 
    s.id,
    s.nombre,
    s.descripcion,
    s.precio,
    s.tipo,
    c.nombre as categoria_nombre
FROM servicios s
LEFT JOIN categorias c ON s.categoria_id = c.id
ORDER BY s.nombre; 