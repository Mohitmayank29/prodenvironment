package com.mohit.proddevenvironmet.Components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mohit.proddevenvironmet.EnumClasses.FileType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

private const val TAG = "FileFlow"
//
//@Composable
//fun FileUploadScreen(
//    type: FileType,
////    viewModel:
//) {
//    val context = LocalContext.current
//    var showSheet by remember { mutableStateOf(false) }
//
//    val scope = rememberCoroutineScope()
//
//    // File launcher
//    val fileLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.OpenDocument()
//    ) { uri ->
//        uri?.let {
//            scope.launch {
//                val file = processFile(context, it, type)
//                file?.let {
//                    viewModel.uploadFile(it)
//                }
//            }
//        }
//    }
//
//    // Gallery
//    val galleryLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri ->
//        uri?.let {
//            scope.launch {
//                val file = processFile(context, it, type)
//                file?.let {
//                    viewModel.uploadFile(it)
//                }
//            }
//        }
//    }
//
//    // UI
//    Column {
//        Button(onClick = { showSheet = true }) {
//            Text("Upload File")
//        }
//    }
//
//    if (showSheet) {
//        FilePickerBottomSheet(
//            type = type,
//            onDismiss = { showSheet = false },
//            onCamera = {
//                // Optional (camera)
//            },
//            onGallery = {
//                showSheet = false
//                galleryLauncher.launch("image/*")
//            },
//            onFile = {
//                showSheet = false
//                fileLauncher.launch(arrayOf("*/*"))
//            }
//        )
//    }
//}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilePickerBottomSheet(
    type: FileType,
    onDismiss: () -> Unit,
    onCamera: () -> Unit,
    onGallery: () -> Unit,
    onFile: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.padding(16.dp)) {

            if (type == FileType.IMAGE || type == FileType.ALL) {
                Text("Camera", modifier = Modifier.clickable { onCamera() })
                Spacer(Modifier.height(8.dp))
                Text("Gallery", modifier = Modifier.clickable { onGallery() })
            }

            Spacer(Modifier.height(8.dp))

            Text("Files", modifier = Modifier.clickable { onFile() })
        }
    }
}
suspend fun processFile(
    context: Context,
    uri: Uri,
    type: FileType
): File? = withContext(Dispatchers.IO) {
    Log.d(TAG, "URI received: $uri")

    val mime = context.contentResolver.getType(uri) ?: return@withContext null
    Log.d(TAG, "MIME type: $mime")
    if (mime == null) {
        Log.e(TAG, "MIME is NULL ❌")
        return@withContext null
    }
    val isValid = when (type) {
        FileType.IMAGE -> mime.startsWith("image")
        FileType.VIDEO -> mime.startsWith("video")
        FileType.FILE -> mime == "application/pdf"
        FileType.ALL -> true
    }
    Log.d(TAG, "Is valid file: $isValid")

    if (!isValid) return@withContext null

    // ✅ Convert
    val file = uriToFile(context, uri)
    Log.d(TAG, "File created: ${file.absolutePath}")
    Log.d(TAG, "File size: ${file.length()} bytes")
    // ✅ Compress if image
    return@withContext if (mime.startsWith("image")) {
        Log.d(TAG, "Compressing image...")
        val compressed =  compressImage(file, context)
        Log.d(TAG, "Compressed file size: ${compressed.length()}")
        compressed
    } else {
        Log.d(TAG, "No compression needed")
        file
    }
}
fun uriToFile(context: Context, uri: Uri): File {
    Log.d(TAG, "Converting URI → File")
    val input = context.contentResolver.openInputStream(uri)!!
    val file = File(context.cacheDir, "file_${System.currentTimeMillis()}")
    if (input == null) {
        Log.e(TAG, "InputStream NULL ❌")
        throw Exception("Cannot open input stream")
    }
    file.outputStream().use {
        input.copyTo(it)
    }
    Log.d(TAG, "File saved at: ${file.absolutePath}")

    return file
}
fun compressImage(file: File, context: Context): File {
    Log.d(TAG, "Compressing file: ${file.name}")

    val bitmap = BitmapFactory.decodeFile(file.absolutePath)
    if (bitmap == null) {
        Log.e(TAG, "Bitmap decode FAILED ❌")
        return file
    }
    val compressed = File(context.cacheDir, "compressed_${file.name}")
    val out = FileOutputStream(compressed)

    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out)

    out.flush()
    out.close()
    Log.d(TAG, "Compression done: ${compressed.absolutePath}")

    return compressed
}
fun createMultipart(file: File,packagename :String): MultipartBody.Part {
    Log.d(TAG, "Creating multipart for: ${file.name}")

    val mime = when (file.extension.lowercase()) {
        "jpg", "jpeg", "png" -> "image/*"
        "mp4" -> "video/mp4"
        "pdf" -> "application/pdf"
        else -> "*/*"
    }
    Log.d(TAG, "Detected MIME: $mime")

    val body = file.asRequestBody(mime.toMediaTypeOrNull())
    Log.d(TAG, "Multipart created ")
    return MultipartBody.Part.createFormData(packagename, file.name, body)
}
fun textPartMap(
    data: Map<String, String>
): Map<String, RequestBody> {

    return data.mapValues { (_, value) ->
        value.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}