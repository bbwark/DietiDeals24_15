package com.CioffiDeVivo.dietideals.data.repositories

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import com.CioffiDeVivo.dietideals.data.network.apiServices.ImageApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

interface ImageRepository {
    suspend fun uploadImage(fileUri: String, context: Context): String
}

class NetworkImageRepository(private val imageApiService: ImageApiService): ImageRepository{
    override suspend fun uploadImage(fileUri: String, context: Context): String {
        val uri = Uri.parse(fileUri)
        val multipartBody = withContext(Dispatchers.IO) {
            createMultipartBodyFromUri(uri, context)
        }
        val response = imageApiService.uploadImage(multipartBody)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    private fun createMultipartBodyFromUri(uri: Uri, context: Context): MultipartBody.Part{
        val contentResolver = context.contentResolver
        val mimeType = contentResolver.getType(uri) ?: "application/octet-stream"
        val fileName = contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            cursor.getString(nameIndex)
        } ?: "file"
        val inputStream = contentResolver.openInputStream(uri) ?: throw FileNotFoundException("Impossible to open URI: $uri")
        val requestBody = inputStream.readBytes().toRequestBody(mimeType.toMediaTypeOrNull())
        inputStream.close()
        return MultipartBody.Part.createFormData("file", fileName, requestBody)
    }

}