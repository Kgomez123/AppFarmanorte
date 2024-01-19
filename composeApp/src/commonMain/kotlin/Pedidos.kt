import kotlinx.serialization.Serializable

@Serializable
data class Pedidos(
	val estado: Int? = null,
	val tipo_pago: Int? = null,
	val codciudad: Int? = null,
	val codpedido: Int? = null,
	val direccion_pedido: String? = null,
	val codcarrito_fk: Int? = null,
	val observacion_adicional: String? = null,
	val ref_e: String? = null,
	val fecha_creacion: String? = null,
	val coddireccion: String? = null
)

