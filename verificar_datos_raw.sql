-- Script para verificar datos raw en la base de datos
-- Ejecutar este script para ver exactamente qué datos están almacenados

-- Verificar datos raw de clientes
SELECT 'DATOS RAW DE CLIENTES:' as info;
SELECT 
    c.id as cliente_id,
    c.usuario_id,
    c.telefono,
    c.direccion,
    c.activo,
    LENGTH(c.telefono) as telefono_longitud,
    LENGTH(c.direccion) as direccion_longitud,
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
ORDER BY c.id;

-- Verificar relación con usuarios
SELECT 'RELACIÓN CON USUARIOS:' as info;
SELECT 
    c.id as cliente_id,
    u.id as usuario_id,
    u.nombre,
    u.email,
    c.telefono,
    c.direccion,
    c.activo,
    LENGTH(c.telefono) as telefono_longitud,
    LENGTH(c.direccion) as direccion_longitud
FROM clientes c
JOIN usuarios u ON c.usuario_id = u.id
WHERE c.activo = true
ORDER BY u.nombre;

-- Verificar si hay datos en las columnas
SELECT 'ANÁLISIS DE COLUMNAS:' as info;
SELECT 
    'telefono' as columna,
    COUNT(*) as total_registros,
    COUNT(CASE WHEN telefono IS NULL THEN 1 END) as registros_null,
    COUNT(CASE WHEN telefono = '' THEN 1 END) as registros_vacios,
    COUNT(CASE WHEN telefono IS NOT NULL AND telefono != '' THEN 1 END) as registros_con_datos
FROM clientes
UNION ALL
SELECT 
    'direccion' as columna,
    COUNT(*) as total_registros,
    COUNT(CASE WHEN direccion IS NULL THEN 1 END) as registros_null,
    COUNT(CASE WHEN direccion = '' THEN 1 END) as registros_vacios,
    COUNT(CASE WHEN direccion IS NOT NULL AND direccion != '' THEN 1 END) as registros_con_datos
FROM clientes; 