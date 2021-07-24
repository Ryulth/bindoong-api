import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin

plugins {
    base
    id("org.jetbrains.kotlin.jvm") version Kotlin.version
    id("org.jetbrains.kotlin.kapt") version Kotlin.version
    id("org.springframework.boot") version Versions.springBoot
    id("org.jetbrains.kotlin.plugin.spring") version Kotlin.version
    id("org.jetbrains.kotlin.plugin.jpa") version Kotlin.version
    id("io.spring.dependency-management") version Versions.springDependencyManagement
}

allprojects {
    group = Project.group
    version = Project.version

    repositories {
        mavenCentral()
        jcenter()

        // for Kotlinx Benchmark
        maven {
            name = "Kotlinx"
            url = uri("https://dl.bintray.com/kotlin/kotlinx")
        }
        maven {
            name = "jitpack"
            url = uri("https://jitpack.io")
        }
    }
}

subprojects {
    apply {
        plugin<JavaLibraryPlugin>()
        plugin<KotlinPlatformJvmPlugin>()

        plugin("kotlin")
        plugin("kotlin-kapt")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("io.spring.dependency-management")
    }

    dependencies {
        implementation("io.github.microutils:kotlin-logging:2.0.6")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.4.3")
    }
    tasks {
        compileJava {
            sourceCompatibility = Jvm.version
            targetCompatibility = Jvm.version
        }

        compileKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = Jvm.version
            }
        }

        compileTestKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = Jvm.version
            }
        }
    }
}

