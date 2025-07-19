package com.example.db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime

object Usuarios : IntIdTable("usuarios") {
    val nombre = varchar("nombre", 100)
    val apellido = varchar("apellido", 100)
    val email = varchar("email", 150).uniqueIndex()
    val contraseña = varchar("contraseña", 255)
    val rol = varchar("rol", 20) // Ejemplo: 'ADMIN' o 'CLIENTE'
    val estado = varchar("estado", 20) // Ejemplo: 'ACTIVO', 'INACTIVO'
    val fechaNacimiento = date("fecha_nacimiento")
    val ultimoLogin = datetime("ultimo_login").nullable()
    val creadoEn = datetime("creado_en")
}

object ReseteoContrasena : IntIdTable("reseteo_contraseña") {
    val usuarioId = reference("usuario_id", Usuarios)
    val token = varchar("token", 255)
    val expiracion = datetime("expiracion")
    val usado = bool("usado").default(false)
}

object Clientes : IntIdTable("clientes") {
    val usuarioId = reference("usuario_id", Usuarios)
    val telefono = varchar("telefono", 20)
    val direccion = varchar("direccion", 255)
}

object Mascotas : IntIdTable("mascotas") {
    val clienteId = reference("cliente_id", Clientes)
    val nombre = varchar("nombre", 100)
    val especie = varchar("especie", 50)
    val raza = varchar("raza", 50)
    val fechaNacimiento = date("fecha_nacimiento")
    val edad = integer("edad")
    val peso = decimal("peso", 5, 2)
    val sexo = varchar("sexo", 10)
    val esterilizado = bool("esterilizado").default(false)
    val vacunasAlDia = bool("vacunas_al_dia").default(false)
    val observaciones = text("observaciones").nullable()
    val estado = varchar("estado", 20) // 'ACTIVA', 'INACTIVA', 'FALLECIDA', 'ADOPTADA'
    val creadoEn = datetime("creado_en")
}

object Servicios : IntIdTable("servicios") {
    val nombre = varchar("nombre", 100)
    val tipo = varchar("tipo", 20) // 'VETERINARIA', 'ESTETICA'
    val descripcion = text("descripcion")
    val precio = decimal("precio", 10, 2)
}

object Citas : IntIdTable("citas") {
    val mascotaId = reference("mascota_id", Mascotas)
    val fecha = datetime("fecha")
    val estado = varchar("estado", 20) // 'PENDIENTE', 'CONFIRMADA', 'CANCELADA', 'COMPLETADA'
    val notas = text("notas").nullable()
    val creadoEn = datetime("creado_en")
}

object CitaServicios : IntIdTable("cita_servicios") {
    val citaId = reference("cita_id", Citas)
    val servicioId = reference("servicio_id", Servicios)
    val precioServicio = decimal("precio_servicio", 10, 2)
}

object HistorialMedico : IntIdTable("historial_medico") {
    val mascotaId = reference("mascota_id", Mascotas)
    val fecha = datetime("fecha")
    val tipo = varchar("tipo", 50) // 'Vacuna', 'Consulta', 'Chequeo'
    val descripcion = text("descripcion")
    val veterinario = varchar("veterinario", 100)
}

object Categorias : IntIdTable("categorias") {
    val nombre = varchar("nombre", 100)
    val descripcion = text("descripcion").nullable()
}

object Productos : IntIdTable("productos") {
    val nombre = varchar("nombre", 150)
    val descripcion = text("descripcion")
    val precio = decimal("precio", 10, 2)
    val stock = integer("stock")
    val imagenUrl = varchar("imagen_url", 255).nullable()
    val categoriaId = reference("categoria_id", Categorias)
}

object Carrito : IntIdTable("carrito") {
    val clienteId = reference("cliente_id", Clientes)
    val productoId = reference("producto_id", Productos)
    val cantidad = integer("cantidad")
}

object Ordenes : IntIdTable("ordenes") {
    val clienteId = reference("cliente_id", Clientes)
    val fecha = datetime("fecha")
    val total = decimal("total", 10, 2)
    val estado = varchar("estado", 20) // 'PENDIENTE', 'PAGADO', 'ENVIADO', 'ENTREGADO'
}

object OrdenDetalles : IntIdTable("orden_detalles") {
    val ordenId = reference("orden_id", Ordenes)
    val productoId = reference("producto_id", Productos)
    val cantidad = integer("cantidad")
    val precioUnitario = decimal("precio_unitario", 10, 2)
}

object ReportesAdmin : IntIdTable("reportes_admin") {
    val fecha = datetime("fecha")
    val tipo = varchar("tipo", 50) // 'VENTAS', 'CITAS', 'STOCK', etc.
    val contenido = text("contenido")
    val generadoPor = reference("generado_por", Usuarios)
}

object HistorialCliente : IntIdTable("historial_cliente") {
    val clienteId = reference("cliente_id", Clientes)
    val accion = varchar("accion", 50) // 'COMPRA', 'CITA', 'LOGIN', 'ACTUALIZACION'
    val descripcion = text("descripcion")
    val fecha = datetime("fecha")
}

object NivelesFidelidad : IntIdTable("niveles_fidelidad") {
    val citasMinimas = integer("citas_minimas")
    val porcentajeDescuento = decimal("porcentaje_descuento", 5, 2)
    val descripcion = text("descripcion").nullable()
}
