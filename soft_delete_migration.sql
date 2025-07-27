-- Script para agregar el campo 'activo' a la tabla clientes existente
-- Ejecutar este script en la base de datos para habilitar soft delete

-- Verificar si la columna ya existe
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'clientes' AND column_name = 'activo'
    ) THEN
        -- Agregar columna 'activo' a la tabla clientes
        ALTER TABLE clientes ADD COLUMN activo BOOLEAN DEFAULT TRUE;
        
        -- Actualizar todos los registros existentes para que estén activos
        UPDATE clientes SET activo = TRUE WHERE activo IS NULL;
        
        -- Crear índice para mejorar el rendimiento de las consultas
        CREATE INDEX idx_clientes_activo ON clientes(activo);
        
        RAISE NOTICE 'Columna activo agregada exitosamente';
    ELSE
        RAISE NOTICE 'La columna activo ya existe';
    END IF;
END $$;

-- Verificar que la columna se agregó correctamente
SELECT column_name, data_type, column_default, is_nullable 
FROM information_schema.columns 
WHERE table_name = 'clientes' AND column_name = 'activo'; 