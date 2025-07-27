-- Script para actualizar clientes existentes con datos de teléfono y dirección
-- Ejecutar este script en la base de datos para completar la información

-- Verificar clientes sin teléfono o dirección
SELECT 'Verificando clientes incompletos...' as info;
SELECT 
    c.id,
    u.nombre,
    c.telefono,
    c.direccion,
    CASE 
        WHEN c.telefono IS NULL OR c.telefono = '' THEN 'SIN TELÉFONO'
        ELSE 'CON TELÉFONO'
    END as estado_telefono,
    CASE 
        WHEN c.direccion IS NULL OR c.direccion = '' THEN 'SIN DIRECCIÓN'
        ELSE 'CON DIRECCIÓN'
    END as estado_direccion
FROM clientes c
JOIN usuarios u ON c.usuario_id = u.id
WHERE c.telefono IS NULL OR c.telefono = '' OR c.direccion IS NULL OR c.direccion = '';

-- Actualizar clientes con datos de teléfono y dirección
UPDATE clientes 
SET 
    telefono = CASE 
        WHEN id = 3 THEN '1234567890'  -- Jose
        WHEN id = 5 THEN '9876543210'  -- Gabriel
        WHEN id = 7 THEN '5551234567'  -- Karen
        WHEN id = 8 THEN '1112223333'  -- Alexis
        ELSE telefono
    END,
    direccion = CASE 
        WHEN id = 3 THEN 'Calle Principal 123, Ciudad Centro'  -- Jose
        WHEN id = 5 THEN 'Avenida Central 456, Barrio Norte'   -- Gabriel
        WHEN id = 7 THEN 'Plaza Mayor 789, Distrito Sur'       -- Karen
        WHEN id = 8 THEN 'Calle Secundaria 321, Zona Este'     -- Alexis
        ELSE direccion
    END
WHERE id IN (3, 5, 7, 8);

-- Verificar el resultado
SELECT 'Verificación después de actualización:' as info;
SELECT 
    c.id,
    u.nombre,
    c.telefono,
    c.direccion,
    c.activo
FROM clientes c
JOIN usuarios u ON c.usuario_id = u.id
ORDER BY u.nombre; 