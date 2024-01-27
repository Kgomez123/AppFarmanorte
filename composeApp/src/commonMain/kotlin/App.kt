import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        Navigator(screen = LoginScreen())//{navigator ->
            //FadeTransition(navigator)
            //SlideTransition(navigator)
            //ScaleTransition(navigator)
        //}
    }
}

class LoginScreen:Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator:Navigator = LocalNavigator.currentOrThrow
        var codigoEmpleado: String by remember { mutableStateOf("") }
        var Identificacion: String by remember { mutableStateOf("") }
        var result: String by remember { mutableStateOf("") }
        val openDialog = remember { mutableStateOf(false) }

        //Layout
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            //Logo
            Spacer(modifier = Modifier.height(100.dp))
            Image(painterResource("nlogo.png"), null, modifier = Modifier.scale(3.toFloat()))

            Spacer(modifier = Modifier.height(50.dp))
            Image(painterResource("logonombre.png"), null)

            //Textbox Codigo
            Spacer(modifier = Modifier.height(80.dp))
            OutlinedTextField(
                value = codigoEmpleado,
                onValueChange = {codigoEmpleado = it},
                label = { Text(text = "Código Empleado") },
                placeholder = { Text(text = "Código Empleado") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "PersonIcon") },
                colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color(0,51,153), focusedLabelColor = Color(0,175,239))
            )

            //TextBox Identificacion
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = Identificacion,
                onValueChange = {Identificacion = it},
                label = { Text(text = "Identificación") },
                placeholder = { Text(text = "Identificación") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "PersonIcon") },
                colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color(0,51,153), focusedLabelColor = Color(0,175,239))
            )

            //Boton Login
            Spacer(modifier = Modifier.height(20.dp))
            AnimatedVisibility(Identificacion.isNotEmpty() && codigoEmpleado.isNotEmpty(), enter = slideInHorizontally (animationSpec = tween(durationMillis = 400) )){
                Button(onClick = {
                    MainScope().launch {
                        kotlin.runCatching {
                            Requests().login(codigoEmpleado, Identificacion)
                        }.onSuccess {
                            result = it
                        }.onFailure {
                            result = it.message.toString()
                        }
                    }
                }, shape = RoundedCornerShape(20.dp), contentPadding = PaddingValues(horizontal = 25.dp, vertical = 10.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(0,51,153), contentColor = Color(0,175,239))) {
                    Text(text = "Continuar")
                }
            }

            //Label login exitoso
            Spacer(modifier = Modifier.height(20.dp))
            AnimatedVisibility(result.isNotEmpty() && result != "null"){
                Text(text = result)
                MainScope().launch {
                    delay(3000)
                    navigator.push(MainScreen())
                }
            }

            //Mostrar Error
            if(result.isNotEmpty()){
                if(result == "null"){
                    AlertDialog(
                        onDismissRequest = {
                            openDialog.value = false
                            result = ""
                        },
                        title = {
                            Text(text = "Error")
                        },
                        text = {
                            Text("No se ha encontrado información del usuario, por favor verifique los datos ingresados e intentelo nuevamente")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    openDialog.value = false
                                    result = ""
                                }) {
                                Text("Confirmar")
                            }
                        }
                    )
                }
            }
        }
    }
}