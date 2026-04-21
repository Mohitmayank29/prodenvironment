@file:Suppress("INFERRED_TYPE_VARIABLE_INTO_EMPTY_INTERSECTION_WARNING",
    "TYPE_INTERSECTION_AS_REIFIED_WARNING"
)

package com.mohit.proddevenvironmet.Screens.submit

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohit.proddevenvironmet.Components.CameraScreen
import com.mohit.proddevenvironmet.Components.FilePickerBottomSheet
import com.mohit.proddevenvironmet.Components.createMultipart
import com.mohit.proddevenvironmet.Components.processFile
import com.mohit.proddevenvironmet.Components.takePhoto
import com.mohit.proddevenvironmet.Components.textPartMap
import com.mohit.proddevenvironmet.EnumClasses.FileType
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


@Composable
fun SubmitScreen( viewModel: SubmitViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }
     val type  = FileType.ALL
    val scope = rememberCoroutineScope()
    val packagename = "image_source"
    val orderId = ""
    val body = mutableMapOf<String, String>()
    body["order_id"] = orderId
    var openCamera by remember { mutableStateOf(false) }
    val converteddata = textPartMap(body)
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.VIDEO_CAPTURE
            )
        }
    }
    if (openCamera) {
        CameraCaptureScreen(
            controller = cameraController,
            onImageCaptured = { bitmap ->
                openCamera = false

                val file = bitmap.toFile(context) // convert bitmap → file
                val imagePart = createMultipart(file, packagename)

                viewModel.submitData(converteddata, imagePart)
            },
            onClose = {
                openCamera = false
            }
        )
    }
    val fileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            scope.launch {
                val file = processFile(context, it, type)
                file?.let {
                    Log.d("UI", "File picked: $uri")
                    Log.d("UI", "Processed file: ${file.absolutePath}")
                    val imagePart = createMultipart(it,packagename)
                    viewModel.submitData(converteddata,imagePart)
                }
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            scope.launch {
                val file = processFile(context, it, type)
                file?.let {
                    Log.d("UI", "File picked: $uri")
                    Log.d("UI", "Processed file: ${file.absolutePath}")
                    val imagepart = createMultipart(it,packagename)
                    viewModel.submitData(converteddata,imagepart)
                }
            }
        }
    }

    if (showSheet) {
        FilePickerBottomSheet(
            type = type,
            onDismiss = { showSheet = false },
            onCamera = {
                openCamera = true
                showSheet = false
            },
            onGallery = {
                showSheet = false
                galleryLauncher.launch("image/*")
            },
            onFile = {
                showSheet = false
                fileLauncher.launch(arrayOf("*/*"))
            }
        )

    }
    Column(Modifier.fillMaxSize()
        .navigationBarsPadding(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                showSheet = true
            },
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp)
                .height(40.dp)
        ) {
            Text(text = "Submit")
        }
    }

}
@Composable
fun CameraCaptureScreen(
    controller: LifecycleCameraController,
    onImageCaptured: (Bitmap) -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current

    Box(Modifier.fillMaxSize()) {

        CameraScreen(controller = controller)

        Column(
            Modifier.fillMaxWidth().navigationBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = {
                takePhoto(controller, context) { bitmap ->
                    bitmap?.let { onImageCaptured(it) }
                }
            }) {
                Text("Capture")
            }

            Button(onClick = onClose) {
                Text("Cancel")
            }
        }
    }
}
fun Bitmap.toFile(context: Context): File {
    val file = File(context.cacheDir, "IMG_${System.currentTimeMillis()}.jpg")

    FileOutputStream(file).use { out ->
        this.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.flush()
    }

    return file
}