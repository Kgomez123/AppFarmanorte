import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.*

class Requests {
    private val client = HttpClient(){install(ContentNegotiation)}

    suspend fun login(codigo: String, id: String): String {
        var result = ""
        val response = client.get("https://plataforma.farmanorteonline.com/consultar-tercero-app?codigo=$codigo&identificacion=$id")
        if(response.bodyAsText() != "null"){
            val empleado: Empleado = response.body()
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
}