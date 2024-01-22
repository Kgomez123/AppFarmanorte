import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class Requests {
    //Creamos cliente Http
    private val client = HttpClient(){install(ContentNegotiation)}

    suspend fun login(codigo: String, id: String): String {
        var result = ""

        //Hacemos request
        val response = client.get("https://plataforma.farmanorteonline.com/consultar-tercero-app?codigo=$codigo&identificacion=$id")
        
        //Jugamos con el resultado
        if(response.bodyAsText() != "null"){
            val empleado: Empleado = Json.decodeFromString(response.bodyAsText())
            if(empleado.genero == 1){
                result = "Bienvenido ${empleado.nombres} ${empleado.apellidos}"
            }else{
                result = "Bienvenida ${empleado.nombres} ${empleado.apellidos}"
            }
        }else{
            result = response.bodyAsText()
        }

        return result
    }

    suspend fun pedidos(): Array<Pedidos> {
        val response = client.get("https://plataforma.farmanorteonline.com/tercero-pedidos")
        val pedidosresult: Array<Pedidos> = Json.decodeFromString<Array<Pedidos>>(response.bodyAsText())
        return  pedidosresult
    }
}