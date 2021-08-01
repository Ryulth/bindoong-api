@file:Suppress("MemberVisibilityCanBePrivate")

object Project {
    const val group = "com.bindoong"
    const val version = "0.0.1"
}

object Plugins {
    const val jvm = "org.jetbrains.kotlin.jvm"
    const val kapt = "org.jetbrains.kotlin.kapt"
    const val spring = "org.jetbrains.kotlin.plugin.spring"
    const val jpa = "org.jetbrains.kotlin.plugin.jpa"
    const val springBoot = "org.springframework.boot"
    const val springDependencyManagement = "io.spring.dependency-management"

}

object Versions {
    const val jvm = "1.8"
    const val kotlin = "1.5.20"
    const val kotlinxCoroutines = "1.5.1"
    const val kotlinLogging = "2.0.6"
    const val springBoot = "2.5.3"
    const val springDependencyManagement = "1.0.11.RELEASE"
    const val flyway = "5.2.1"
    const val javaJwt = "3.15.0"
    const val r2dbcMysql = "0.8.2.RELEASE"
    const val swagger = "3.0.0"
    const val guava = "30.1.1-jre"
    const val jackson = "2.12.4"
}

object Libs {
    object Kotlin {
        const val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
        const val logging = "io.github.microutils:kotlin-logging:${Versions.kotlinLogging}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
        const val coroutinesReactor = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${Versions.kotlinxCoroutines}"
    }
    object SpringBoot {
        const val autoconfigure = "org.springframework.boot:spring-boot-autoconfigure"
        const val webflux = "org.springframework.boot:spring-boot-starter-webflux"
        const val actuator = "org.springframework.boot:spring-boot-starter-actuator"
        const val security = "org.springframework.boot:spring-boot-starter-security"
        const val test = "org.springframework.boot:spring-boot-starter-test"
    }

    object SpringData {
        const val r2dbc = "org.springframework.data:spring-data-r2dbc"
        const val r2dbcMysql = "dev.miku:r2dbc-mysql:${Versions.r2dbcMysql}"
        const val relational = "org.springframework.data:spring-data-relational"
    }
    object Jackson {
        const val bom = "com.fasterxml.jackson:jackson-bom:${Versions.jackson}"
        const val annotations = "com.fasterxml.jackson.core:jackson-annotations"
        const val datatypeJdk8 = "com.fasterxml.jackson.datatype:jackson-datatype-jdk8"
        const val datatypeJsr310 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310"
        const val moduleKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin"
        const val moduleAfterburner = "com.fasterxml.jackson.module:jackson-module-afterburner"
    }
    object Auth0 {
        const val javaJwt = "com.auth0:java-jwt:${Versions.javaJwt}"
    }
    object Google {
        const val guava = "com.google.guava:guava:${Versions.guava}"
    }
    object SpringFox {
        const val starter = "io.springfox:springfox-boot-starter:${Versions.swagger}"
    }
}