-- Script para insertar datos de prueba si no hay clientes
-- Ejecutar este script en la base de datos para tener datos de prueba

-- Verificar si hay clientes
SELECT 'Verificando clientes existentes...' as info;
SELECT COUNT(*) as total_clientes FROM clientes;

-- Si no hay clientes, insertar datos de prueba
DO $$
DECLARE
    cliente_count INTEGER;
    usuario_id_1 INTEGER;
    usuario_id_2 INTEGER;
    usuario_id_3 INTEGER;
    usuario_id_4 INTEGER;
BEGIN
    -- Contar clientes existentes
    SELECT COUNT(*) INTO cliente_count FROM clientes;
    
    IF cliente_count = 0 THEN
        RAISE NOTICE 'No hay clientes, insertando datos de prueba...';
        
        -- Insertar usuarios de prueba
        INSERT INTO usuarios (nombre, apellido, email, contraseña, rol, estado, fecha_nacimiento, creado_en)
        VALUES 
            ('Juan', 'Pérez', 'juan.perez@gmail.com', '$2a$10$hashedpassword', 'CLIENTE', 'ACTIVO', '1990-01-15', NOW()),
            ('María', 'García', 'maria.garcia@hotmail.com', '$2a$10$hashedpassword', 'CLIENTE', 'ACTIVO', '1985-03-22', NOW()),
            ('Carlos', 'López', 'carlos.lopez@outlook.com', '$2a$10$hashedpassword', 'CLIENTE', 'ACTIVO', '1992-07-10', NOW()),
            ('Ana', 'Martínez', 'ana.martinez@yahoo.com', '$2a$10$hashedpassword', 'CLIENTE', 'ACTIVO', '1988-11-05', NOW())
        RETURNING id INTO usuario_id_1;
        
        -- Obtener los IDs de los usuarios insertados
        SELECT id INTO usuario_id_1 FROM usuarios WHERE email = 'juan.perez@gmail.com';
        SELECT id INTO usuario_id_2 FROM usuarios WHERE email = 'maria.garcia@hotmail.com';
        SELECT id INTO usuario_id_3 FROM usuarios WHERE email = 'carlos.lopez@outlook.com';
        SELECT id INTO usuario_id_4 FROM usuarios WHERE email = 'ana.martinez@yahoo.com';
        
        -- Insertar clientes con teléfono y dirección
        INSERT INTO clientes (usuario_id, telefono, direccion, activo)
        VALUES 
            (usuario_id_1, '1234567890', 'Calle Principal 123, Ciudad Centro', true),
            (usuario_id_2, '9876543210', 'Avenida Central 456, Barrio Norte', true),
            (usuario_id_3, '5551234567', 'Plaza Mayor 789, Distrito Sur', true),
            (usuario_id_4, '1112223333', 'Calle Secundaria 321, Zona Este', true);
        
        RAISE NOTICE 'Datos de prueba insertados exitosamente';
    ELSE
        RAISE NOTICE 'Ya existen % clientes en la base de datos', cliente_count;
    END IF;
END $$;

-- Verificar el resultado
SELECT 'Verificación final:' as info;
SELECT 
    c.id,
    u.nombre,
    u.email,
    c.telefono,
    c.direccion,
    c.activo
FROM clientes c
JOIN usuarios u ON c.usuario_id = u.id
ORDER BY u.nombre; 