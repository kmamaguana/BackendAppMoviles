-- Script de migración específico para PostgreSQL
-- Agrega la columna 'activo' a la tabla clientes de manera segura

-- Función para verificar si una columna existe
CREATE OR REPLACE FUNCTION column_exists(table_name text, column_name text)
RETURNS boolean AS $$
BEGIN
    RETURN EXISTS (
        SELECT 1 
        FROM information_schema.columns 
        WHERE table_name = $1 
        AND column_name = $2
    );
END;
$$ LANGUAGE plpgsql;

-- Agregar columna activo si no existe
DO $$
BEGIN
    IF NOT column_exists('clientes', 'activo') THEN
        -- Agregar la columna activo
        ALTER TABLE clientes ADD COLUMN activo BOOLEAN DEFAULT TRUE;
        
        -- Actualizar registros existentes
        UPDATE clientes SET activo = TRUE WHERE activo IS NULL;
        
        -- Crear índice para mejorar rendimiento
        CREATE INDEX idx_clientes_activo ON clientes(activo);
        
        RAISE NOTICE 'Columna activo agregada exitosamente a la tabla clientes';
    ELSE
        RAISE NOTICE 'La columna activo ya existe en la tabla clientes';
    END IF;
END $$;

-- Verificar el resultado
SELECT 
    'Verificación de migración:' as info,
    COUNT(*) as total_clientes,
    COUNT(CASE WHEN activo = TRUE THEN 1 END) as clientes_activos,
    COUNT(CASE WHEN activo = FALSE THEN 1 END) as clientes_inactivos
FROM clientes;

-- Limpiar función temporal
DROP FUNCTION IF EXISTS column_exists(text, text); 