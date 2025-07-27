-- Script simple para agregar el campo 'activo' a la tabla clientes
-- Compatible con PostgreSQL, MySQL y SQLite

-- Para PostgreSQL
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'clientes' AND column_name = 'activo'
    ) THEN
        ALTER TABLE clientes ADD COLUMN activo BOOLEAN DEFAULT TRUE;
        UPDATE clientes SET activo = TRUE WHERE activo IS NULL;
        RAISE NOTICE 'Columna activo agregada exitosamente';
    ELSE
        RAISE NOTICE 'La columna activo ya existe';
    END IF;
END $$;

-- Para MySQL (comentar si usas PostgreSQL)
/*
ALTER TABLE clientes ADD COLUMN activo BOOLEAN DEFAULT TRUE;
UPDATE clientes SET activo = TRUE WHERE activo IS NULL;
*/

-- Para SQLite (comentar si usas PostgreSQL o MySQL)
/*
ALTER TABLE clientes ADD COLUMN activo INTEGER DEFAULT 1;
UPDATE clientes SET activo = 1 WHERE activo IS NULL;
*/

-- Verificar resultado
SELECT 'Verificación:' as info;
SELECT COUNT(*) as total_clientes FROM clientes;
SELECT COUNT(*) as clientes_activos FROM clientes WHERE activo = TRUE; 