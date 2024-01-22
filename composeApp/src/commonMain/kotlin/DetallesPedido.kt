
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi

class DetallesPedido(pedidos: Pedidos): Screen {
    private val pedidos = pedidos

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow
        Column(Modifier.fillMaxWidth().fillMaxHeight().verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Pedido N° ${pedidos.codpedido}")
            Spacer(modifier = Modifier.height(20.dp))
            Text("Dirección: ${pedidos.direccion_pedido}")
            Text("Pago:  ${pedidos.tipo_pago}")
            Text("Estado:  ${pedidos.estado}")
            Text("Observaciones:  ${pedidos.observacion_adicional}")
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {

            }, shape = RoundedCornerShape(20.dp), contentPadding = PaddingValues(horizontal = 25.dp, vertical = 10.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(0,51,153), contentColor = Color(0,175,239))) {
                Text(text = "Seguimiento")
            }
        }
    }
}