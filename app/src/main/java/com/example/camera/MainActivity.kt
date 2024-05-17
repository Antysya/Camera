package com.example.camera

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.camera.ui.theme.CameraTheme
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //здесь запросить права на камеру
        val responseCam = askCameraPermissions();
        //запросить права на медиа
        val responseMedia = askMediaPermissions();
        setContent {
            CameraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(
                        name = responseCam
                    )
                }
            }
        }
    }

    private fun askCameraPermissions(): String {
        var result= ""
        //создаем функцию для запроса прав
        val cameraPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.CAMERA, false) -> {
                    // Предоставлены права на использование камеры
                    //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                    result = "Permission granted"
                } else -> {
                    // отказано пользователем в доступе к камере
                    result = "No camera permission"
                    //return@registerForActivityResult
                }
            }
        }
        //проверяем вдруг права уже выданы
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //если права еще не выданы, вызываем функцию для запроса прав
            cameraPermissionRequest.launch(arrayOf(
                Manifest.permission.CAMERA))
        } else {
            result = "We have camera permission"
            }
        return result
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun askMediaPermissions(): String {
        var result= ""
        //создаем функцию для запроса прав
        val mediaPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED, false) -> {
                    // Предоставлены права на считывать изображения или видеофайлы из внешнего хранилища, выбранные пользователем с помощью средства выбора фотографий с запросом разрешения
                    //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                    result = "Permission granted"
                } else -> {
                // отказано пользователем в доступе
                result = "No media permission"
                //return@registerForActivityResult
            }
            }
        }
        //проверяем вдруг права уже выданы
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //если права еще не выданы, вызываем функцию для запроса прав
            mediaPermissionRequest.launch(arrayOf(
                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED))
        } else {
            result = "We have media permission"
        }
        return result
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CameraTheme {
        Greeting("Android")
    }
}