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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi

class Seguimiento(private val pedidos: Pedidos): Screen {
    @OptIn(ExperimentalResourceApi::class)

    @Composable
    override fun Content() {
        var observaciones: String by remember { mutableStateOf("") }
        LocalNavigator.currentOrThrow
        Column(Modifier.fillMaxWidth().fillMaxHeight().verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally){
            Text("Añadir Seguimiento")
            Spacer(modifier = Modifier.height(20.dp))
            Text("Observaciones:")
            OutlinedTextField(
                value = observaciones,
                onValueChange = {observaciones = it},
                modifier = Modifier.height(500.dp),
                label = { Text(text = "Observaciones") },
                placeholder = { Text(text = "Ingrese comentarios u observaciones del pedido") },
                colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color(0,51,153), focusedLabelColor = Color(0,175,239))
            )
            //Cargar Imagen...
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {
                //Codigo para Enviar imagen y observaciones...
            }, shape = RoundedCornerShape(20.dp), contentPadding = PaddingValues(horizontal = 25.dp, vertical = 10.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(0,51,153), contentColor = Color(0,175,239))) {
                Text(text = "Enviar")
            }
        }
    }
}