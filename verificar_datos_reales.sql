-- Script para verificar los datos reales en la base de datos
-- Ejecutar este script para ver exactamente qué datos están almacenados

-- Verificar estructura de la tabla clientes
SELECT 'Estructura de la tabla clientes:' as info;
SELECT column_name, data_type, is_nullable, column_default
FROM information_schema.columns 
WHERE table_name = 'clientes' 
ORDER BY ordinal_position;

-- Verificar todos los clientes con sus datos reales
SELECT 'Datos reales de todos los clientes:' as info;
SELECT 
    c.id,
    u.nombre,
    c.telefono,
    c.direccion,
    c.activo,
    c.usuario_id,
    LENGTH(c.telefono) as longitud_telefono,
    LENGTH(c.direccion) as longitud_direccion,
    CASE 
        WHEN c.telefono IS NULL THEN 'NULL'
        WHEN c.telefono = '' THEN 'VACÍO'
        ELSE 'CON DATO'
    END as estado_telefono,
    CASE 
        WHEN c.direccion IS NULL THEN 'NULL'
        WHEN c.direccion = '' THEN 'VACÍO'
        ELSE 'CON DATO'
    END as estado_direccion
FROM clientes c
JOIN usuarios u ON c.usuario_id = u.id
ORDER BY u.nombre;

-- Verificar solo clientes activos
SELECT 'Solo clientes activos:' as info;
SELECT 
    c.id,
    u.nombre,
    c.telefono,
    c.direccion,
    c.activo
FROM clientes c
JOIN usuarios u ON c.usuario_id = u.id
WHERE c.activo = true
ORDER BY u.nombre;

-- Verificar si hay clientes con datos
SELECT 'Clientes con teléfono o dirección:' as info;
SELECT 
    c.id,
    u.nombre,
    c.telefono,
    c.direccion
FROM clientes c
JOIN usuarios u ON c.usuario_id = u.id
WHERE (c.telefono IS NOT NULL AND c.telefono != '') 
   OR (c.direccion IS NOT NULL AND c.direccion != '')
ORDER BY u.nombre; 