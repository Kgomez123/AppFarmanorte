package org.farmanorte.appempleados

import App
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
            checkPermissions()
        }
    }

    private fun checkPermissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestLocationPermissions()
        }else{
            Intent(applicationContext, LocationService::class.java).apply{
                action = LocationService.ACTION_START
                startService(this)
            }
        }
    }

    private fun requestLocationPermissions(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 777)
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 777){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent(applicationContext, LocationService::class.java).apply{
                    action = LocationService.ACTION_START
                    startService(this)
                }
            }else{
                Toast.makeText(this, "Por favor, ir a ajustes y aceptar los permisos de ubicacion", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}