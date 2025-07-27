-- Script para insertar datos de prueba de clientes con teléfono y dirección
-- Ejecutar este script en la base de datos para tener datos de prueba

-- Primero, verificar si ya existen clientes
SELECT COUNT(*) as total_clientes FROM clientes;

-- Insertar usuarios de prueba (si no existen)
INSERT INTO usuarios (nombre, apellido, email, contraseña, rol, estado, fecha_nacimiento, creado_en)
VALUES 
    ('Juan', 'Pérez', 'juan.perez@gmail.com', '$2a$10$hashedpassword', 'CLIENTE', 'ACTIVO', '1990-01-15', NOW()),
    ('María', 'García', 'maria.garcia@hotmail.com', '$2a$10$hashedpassword', 'CLIENTE', 'ACTIVO', '1985-03-22', NOW()),
    ('Carlos', 'López', 'carlos.lopez@outlook.com', '$2a$10$hashedpassword', 'CLIENTE', 'ACTIVO', '1992-07-10', NOW()),
    ('Ana', 'Martínez', 'ana.martinez@yahoo.com', '$2a$10$hashedpassword', 'CLIENTE', 'ACTIVO', '1988-11-05', NOW())
ON CONFLICT (email) DO NOTHING;

-- Obtener los IDs de los usuarios insertados
SELECT id, nombre, email FROM usuarios WHERE email IN (
    'juan.perez@gmail.com',
    'maria.garcia@hotmail.com', 
    'carlos.lopez@outlook.com',
    'ana.martinez@yahoo.com'
);

-- Insertar clientes con teléfono y dirección
INSERT INTO clientes (usuario_id, telefono, direccion, activo)
VALUES 
    ((SELECT id FROM usuarios WHERE email = 'juan.perez@gmail.com'), '1234567890', 'Calle Principal 123, Ciudad Centro', true),
    ((SELECT id FROM usuarios WHERE email = 'maria.garcia@hotmail.com'), '9876543210', 'Avenida Central 456, Barrio Norte', true),
    ((SELECT id FROM usuarios WHERE email = 'carlos.lopez@outlook.com'), '5551234567', 'Plaza Mayor 789, Distrito Sur', true),
    ((SELECT id FROM usuarios WHERE email = 'ana.martinez@yahoo.com'), '1112223333', 'Calle Secundaria 321, Zona Este', true)
ON CONFLICT (usuario_id) DO UPDATE SET
    telefono = EXCLUDED.telefono,
    direccion = EXCLUDED.direccion,
    activo = EXCLUDED.activo;

-- Verificar los datos insertados
SELECT 
    c.id,
    u.nombre,
    u.email,
    c.telefono,
    c.direccion,
    c.activo
FROM clientes c
JOIN usuarios u ON c.usuario_id = u.id
WHERE u.email IN (
    'juan.perez@gmail.com',
    'maria.garcia@hotmail.com',
    'carlos.lopez@outlook.com', 
    'ana.martinez@yahoo.com'
)
ORDER BY u.nombre; 