import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.*

class Requests {
    //Creamos cliente Http
    private val client = HttpClient(){install(ContentNegotiation)}

    suspend fun login(codigo: String, id: String): String {
        var result = ""

        //Hacemos request
        val response = client.get("https://plataforma.farmanorteonline.com/consultar-tercero-app?codigo=$codigo&identificacion=$id")
        
        //Jugamos con el resultado
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