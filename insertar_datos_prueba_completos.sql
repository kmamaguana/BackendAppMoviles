-- Script para insertar datos de prueba completos
-- Ejecutar este script si no hay datos en las tablas

-- Insertar usuarios de prueba
INSERT INTO usuarios (nombre, apellido, email, contraseña, rol, estado, fecha_nacimiento, creado_en) VALUES
('Admin', 'Sistema', 'admin@admin.com', '$2a$10$hash_placeholder', 'ADMIN', 'ACTIVO', '1990-01-01', NOW()),
('Jose', 'García', 'jose@gmail.com', '$2a$10$hash_placeholder', 'CLIENTE', 'ACTIVO', '1995-05-15', NOW()),
('Gabriel', 'López', 'gabriel@hotmail.com', '$2a$10$hash_placeholder', 'CLIENTE', 'ACTIVO', '1988-12-20', NOW()),
('Karen', 'Martínez', 'karen@outlook.com', '$2a$10$hash_placeholder', 'CLIENTE', 'ACTIVO', '1992-08-10', NOW()),
('Alexis', 'Rodríguez', 'alexis@gmail.com', '$2a$10$hash_placeholder', 'CLIENTE', 'ACTIVO', '1990-03-25', NOW())
ON CONFLICT (email) DO NOTHING;

-- Obtener IDs de usuarios insertados
DO $$
DECLARE
    admin_id INTEGER;
    jose_id INTEGER;
    gabriel_id INTEGER;
    karen_id INTEGER;
    alexis_id INTEGER;
BEGIN
    -- Obtener IDs
    SELECT id INTO admin_id FROM usuarios WHERE email = 'admin@admin.com';
    SELECT id INTO jose_id FROM usuarios WHERE email = 'jose@gmail.com';
    SELECT id INTO gabriel_id FROM usuarios WHERE email = 'gabriel@hotmail.com';
    SELECT id INTO karen_id FROM usuarios WHERE email = 'karen@outlook.com';
    SELECT id INTO alexis_id FROM usuarios WHERE email = 'alexis@gmail.com';
    
    -- Insertar clientes
    INSERT INTO clientes (usuario_id, telefono, direccion, activo) VALUES
    (jose_id, '1234567890', 'Calle Principal 123, Ciudad Centro', true),
    (gabriel_id, '9876543210', 'Avenida Central 456, Barrio Norte', true),
    (karen_id, '5551234567', 'Plaza Mayor 789, Distrito Sur', true),
    (alexis_id, '1112223333', 'Calle Secundaria 321, Zona Este', true)
    ON CONFLICT (usuario_id) DO NOTHING;
    
    RAISE NOTICE 'Datos de prueba insertados correctamente';
END $$;

-- Verificar datos insertados
SELECT 'VERIFICACIÓN DESPUÉS DE INSERCIÓN:' as info;
SELECT 'Usuarios:' as tabla, COUNT(*) as total FROM usuarios
UNION ALL
SELECT 'Clientes:' as tabla, COUNT(*) as total FROM clientes;

-- Mostrar relación final
SELECT 'RELACIÓN FINAL:' as info;
SELECT 
    u.id as usuario_id,
    u.nombre,
    u.email,
    u.rol,
    c.id as cliente_id,
    c.telefono,
    c.direccion,
    c.activo
FROM usuarios u
LEFT JOIN clientes c ON u.id = c.usuario_id
ORDER BY u.id; 