package com.bindoong.infrastructure.config

import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.http.Protocol
import software.amazon.awssdk.http.async.SdkAsyncHttpClient
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.S3Configuration
import java.time.Duration

@Configuration
class S3AsyncClientConfig(
    @Value("\${aws.s3.region}")
    private val region: String,
) {
    @Bean
    fun s3AsyncClient(): S3AsyncClient = S3AsyncClient.builder()
        .httpClient(getHttpClient())
        .serviceConfiguration(getServiceConfiguration())
        .region(getS3Region())
        .credentialsProvider(getS3CredentialProvider())
        .build()

    private fun getHttpClient(): SdkAsyncHttpClient = NettyNioAsyncHttpClient.builder()
        .protocol(Protocol.HTTP1_1)
        .tcpKeepAlive(true)
        .writeTimeout(WRITE_TIMEOUT)
        .readTimeout(READ_TIMEOUT)
        .connectionTimeout(CONNECTION_TIMEOUT)
        .connectionAcquisitionTimeout(CONNECTION_ACQUISITION_TIMEOUT)
        .maxConcurrency(MAX_CONCURRENCY)
        .maxPendingConnectionAcquires(MAX_PENDING_CONNECTION_ACQUIRE)
        .build()

    private fun getServiceConfiguration(): S3Configuration = S3Configuration.builder()
        .checksumValidationEnabled(false)
        .chunkedEncodingEnabled(true)
        .accelerateModeEnabled(false)
        .dualstackEnabled(true)
        .build()

    private fun getS3Region(): Region = Region.of(region)

    private fun getS3CredentialProvider(): AwsCredentialsProvider = DefaultCredentialsProvider.create()

    companion object : KLogging() {
        private const val MAX_CONCURRENCY = 10000
        private const val MAX_PENDING_CONNECTION_ACQUIRE = 10000

        private val CONNECTION_TIMEOUT = Duration.ofSeconds(5)
        private val CONNECTION_ACQUISITION_TIMEOUT = Duration.ofSeconds(5)
        private val WRITE_TIMEOUT = Duration.ofMinutes(3)
        private val READ_TIMEOUT = Duration.ofMinutes(3)
    }
}
