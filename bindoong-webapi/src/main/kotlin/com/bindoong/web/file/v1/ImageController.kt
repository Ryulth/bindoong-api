package com.bindoong.web.file.v1

import com.bindoong.service.file.ImageService
import com.bindoong.service.file.UploadImageParameter
import com.bindoong.web.dto.ImageDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.withContext
import mu.KLogging
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.codec.multipart.FilePart
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/files/images")
class ImageController(
    private val imageService: ImageService
) {
    @Operation(
        operationId = "uploadImage",
        summary = "이미지 업로드",
    )
    @ApiResponse(responseCode = "200", description = "이미지 full url 내려드림")
    @PreAuthorize("hasRole('BASIC')")
    @PostMapping
    suspend fun uploadImage(
        @RequestPart image: FilePart
    ): ImageDto = withContext(Dispatchers.IO) {
        val content: Flow<DataBuffer> = image.content().asFlow()
        val contentType = image.headers().contentType?.toString()?.takeIf { isImage(it) }
            ?: throw IllegalArgumentException("Illegal image type")

        val imageUrl = imageService.uploadImage(
            UploadImageParameter(
                filename = image.filename(),
                contentType = contentType,
                contentLength = content.map { it.readableByteCount().toLong() }.reduce { a, b -> a + b },
                content = content.map { it.asByteBuffer() }
            )
        )
        ImageDto(imageUrl = imageUrl)
    }

    companion object : KLogging() {
        private const val TYPE_IMAGE = "image"
        private val isImage = { contentType: String -> contentType.split("/")[0] == TYPE_IMAGE }
    }
}
