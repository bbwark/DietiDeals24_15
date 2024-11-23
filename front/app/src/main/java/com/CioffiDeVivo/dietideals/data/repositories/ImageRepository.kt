package com.CioffiDeVivo.dietideals.data.repositories

import android.content.Context
import android.net.Uri
import com.CioffiDeVivo.dietideals.data.network.apiServices.ImageApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

interface ImageRepository {
    suspend fun uploadImage(fileUri: Uri, fileName: String, context: Context): String
}

class NetworkImageRepository(private val imageApiService: ImageApiService): ImageRepository{
    override suspend fun uploadImage(fileUri: Uri, fileName: String, context: Context): String {
        val file = uriToFile(fileUri, context)
        val fileRequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)
        val fileNameRequestBody = fileName.toRequestBody("text/plain".toMediaTypeOrNull())

        val response = imageApiService.uploadImage(filePart, fileNameRequestBody)
        if(response.isSuccessful){
            return response.body() ?: throw IllegalStateException("Response BODY is NULL!")
        } else{
            throw HttpException(response)
        }
    }

    private fun uriToFile(uri: Uri, context: Context): File{
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: throw IOException("Unable to open URI: $uri")
        val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        inputStream.close()
        return tempFile
    }

}