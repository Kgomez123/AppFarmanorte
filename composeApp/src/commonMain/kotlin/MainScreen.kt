
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi

class MainScreen: Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow
        var pedidos = remember { mutableStateListOf<Pedidos>() }
        val error = remember { mutableStateOf(false) }
        val consultado = remember { mutableStateOf(false) }
        val texto = remember { mutableStateOf(false) }
        val openDialog = remember { mutableStateOf(false) }
        if(!consultado.value){
            MainScope().launch {
                kotlin.runCatching {

                    Requests().pedidos()
                }.onSuccess {
                    for (its in it){
                        pedidos += its
                    }
                    consultado.value = true
                    texto.value = true
                }.onFailure {
                    error.value = true
                }
            }
        }

        if(error.value){
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "Error")
                },
                text = {
                    Text("No se ha podido consultar la información, intentelo nuevamente")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                            error.value = false
                            navigator.pop()
                        }) {
                        Text("Confirmar")
                    }
                }
            )
        }

        if(texto.value){
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Estado 1")
                for(pedido in pedidos){
                    if(pedido.estado == 1){
                        Spacer(modifier = Modifier.height(50.dp))
                        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight(), verticalAlignment = Alignment.CenterVertically){
                            Column(modifier = Modifier.fillMaxWidth(0.7f)) { Text("${pedido.toString()} ") }
                            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                Button(onClick = {
                                    MainScope().launch {
                                        navigator?.push(DetallesPedido(pedido))
                                    }
                                }, shape = CircleShape, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0,51,153))) {
                                    Icon(
                                        imageVector = Icons.TwoTone.CheckCircle,
                                        contentDescription ="Detalles",
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))
                Text("Estado 2")
                for(pedido in pedidos){
                    if(pedido.estado == 2){
                        Spacer(modifier = Modifier.height(50.dp))
                        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight(), verticalAlignment = Alignment.CenterVertically){
                            Column(modifier = Modifier.fillMaxWidth(0.7f)) { Text("${pedido.toString()} ") }
                            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                Button(onClick = {
                                    MainScope().launch {
                                        navigator?.push(DetallesPedido(pedido))
                                    }
                                }, shape = CircleShape, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0,51,153))) {
                                    Icon(
                                        imageVector = Icons.TwoTone.CheckCircle,
                                        contentDescription ="Detalles",
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}