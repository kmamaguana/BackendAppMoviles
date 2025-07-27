-- Verificación simple de datos en las tablas
-- Ejecutar este script para ver si hay datos

-- Verificar si hay usuarios
SELECT 'VERIFICACIÓN DE USUARIOS:' as info;
SELECT COUNT(*) as total_usuarios FROM usuarios;

-- Mostrar todos los usuarios
SELECT 'TODOS LOS USUARIOS:' as info;
SELECT id, nombre, apellido, email, rol FROM usuarios ORDER BY id;

-- Verificar si hay clientes
SELECT 'VERIFICACIÓN DE CLIENTES:' as info;
SELECT COUNT(*) as total_clientes FROM clientes;

-- Mostrar todos los clientes
SELECT 'TODOS LOS CLIENTES:' as info;
SELECT id, usuario_id, telefono, direccion, activo FROM clientes ORDER BY id;

-- Verificar relación simple
SELECT 'RELACIÓN SIMPLE:' as info;
SELECT 
    u.id as usuario_id,
    u.nombre,
    u.email,
    c.id as cliente_id,
    c.telefono,
    c.direccion
FROM usuarios u
LEFT JOIN clientes c ON u.id = c.usuario_id
ORDER BY u.id; 