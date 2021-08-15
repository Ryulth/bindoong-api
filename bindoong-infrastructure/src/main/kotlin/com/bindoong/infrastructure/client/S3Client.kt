package com.bindoong.infrastructure.client

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactor.asFlux
import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.async.AsyncRequestBody
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.nio.ByteBuffer
import java.time.LocalDate
import java.util.UUID

@Service
class S3Client(
    private val client: S3AsyncClient
) {
    suspend fun upload(parameter: UploadParameter): String {
        val ext = FilenameUtils.getExtension(parameter.filename)?.let { ".$it" } ?: ""
        val key = "${parameter.prefix}/${LocalDate.now().toString().replace("-", "")}/${UUID.randomUUID()}$ext"
        val request = PutObjectRequest.builder()
            .bucket(parameter.bucketName)
            .key(key)
            .contentType(parameter.contentType)
            .contentLength(parameter.contentLength)
            .build()
        client.putObject(request, AsyncRequestBody.fromPublisher(parameter.content.asFlux())).await()
        return key
    }
}

data class UploadParameter(
    val bucketName: String,
    val prefix: String,
    val filename: String,
    val contentType: String,
    val contentLength: Long,
    val content: Flow<ByteBuffer>,
    val cacheControl: String? = null
)
