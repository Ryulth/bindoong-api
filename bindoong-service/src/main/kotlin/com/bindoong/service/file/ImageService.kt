package com.bindoong.service.file

import com.bindoong.infrastructure.client.S3Client
import com.bindoong.infrastructure.client.UploadParameter
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.ByteBuffer

@Service
class ImageService(
    private val s3Client: S3Client,
    @Value("\${aws.s3.images-bucket}")
    private val imagesBucket: String,
    @Value("\${aws.cloud-front.images-domain}")
    private val imagesPrefix: String,
) {
    suspend fun uploadImage(parameter: UploadImageParameter): String {
        val urlPostFix = s3Client.upload(
            UploadParameter(
                bucketName = imagesBucket,
                prefix = PREFIX_IMAGES,
                filename = parameter.filename,
                contentType = parameter.contentType,
                contentLength = parameter.contentLength,
                content = parameter.content
            )
        )
        return "$imagesPrefix/$urlPostFix"
    }

    companion object {
        private const val PREFIX_IMAGES = "images"
    }
}

data class UploadImageParameter(
    val filename: String,
    val contentType: String,
    val contentLength: Long,
    val content: Flow<ByteBuffer>
)
