-- Script para verificar datos en tablas usuarios y clientes
-- Ejecutar este script para ver la relación entre ambas tablas

-- Verificar estructura de ambas tablas
SELECT 'Estructura de la tabla usuarios:' as info;
SELECT column_name, data_type, is_nullable, column_default
FROM information_schema.columns 
WHERE table_name = 'usuarios' 
ORDER BY ordinal_position;

SELECT 'Estructura de la tabla clientes:' as info;
SELECT column_name, data_type, is_nullable, column_default
FROM information_schema.columns 
WHERE table_name = 'clientes' 
ORDER BY ordinal_position;

-- Verificar todos los usuarios
SELECT 'Todos los usuarios:' as info;
SELECT 
    id,
    nombre,
    apellido,
    email,
    rol,
    estado,
    fecha_nacimiento,
    creado_en
FROM usuarios
ORDER BY nombre;

-- Verificar todos los clientes
SELECT 'Todos los clientes:' as info;
SELECT 
    id,
    usuario_id,
    telefono,
    direccion,
    activo
FROM clientes
ORDER BY id;

-- Verificar la relación entre usuarios y clientes
SELECT 'Relación usuarios-clientes (JOIN):' as info;
SELECT 
    u.id as usuario_id,
    u.nombre,
    u.apellido,
    u.email,
    u.rol,
    c.id as cliente_id,
    c.telefono,
    c.direccion,
    c.activo,
    CASE 
        WHEN c.id IS NULL THEN 'USUARIO SIN CLIENTE'
        ELSE 'USUARIO CON CLIENTE'
    END as estado_relacion
FROM usuarios u
LEFT JOIN clientes c ON u.id = c.usuario_id
ORDER BY u.nombre;

-- Verificar solo usuarios que son clientes
SELECT 'Usuarios que son clientes:' as info;
SELECT 
    u.id as usuario_id,
    u.nombre,
    u.apellido,
    u.email,
    u.rol,
    c.id as cliente_id,
    c.telefono,
    c.direccion,
    c.activo
FROM usuarios u
INNER JOIN clientes c ON u.id = c.usuario_id
WHERE c.activo = true
ORDER BY u.nombre;

-- Verificar usuarios que NO son clientes
SELECT 'Usuarios que NO son clientes:' as info;
SELECT 
    u.id,
    u.nombre,
    u.apellido,
    u.email,
    u.rol
FROM usuarios u
LEFT JOIN clientes c ON u.id = c.usuario_id
WHERE c.id IS NULL
ORDER BY u.nombre;

-- Verificar clientes sin usuario (no debería haber)
SELECT 'Clientes sin usuario (error):' as info;
SELECT 
    c.id,
    c.usuario_id,
    c.telefono,
    c.direccion,
    c.activo
FROM clientes c
LEFT JOIN usuarios u ON c.usuario_id = u.id
WHERE u.id IS NULL;

-- Resumen de la relación
SELECT 'Resumen de la relación:' as info;
SELECT 
    'Total usuarios' as tipo,
    COUNT(*) as cantidad
FROM usuarios
UNION ALL
SELECT 
    'Total clientes' as tipo,
    COUNT(*) as cantidad
FROM clientes
UNION ALL
SELECT 
    'Usuarios que son clientes' as tipo,
    COUNT(*) as cantidad
FROM usuarios u
INNER JOIN clientes c ON u.id = c.usuario_id
UNION ALL
SELECT 
    'Clientes activos' as tipo,
    COUNT(*) as cantidad
FROM clientes
WHERE activo = true; 