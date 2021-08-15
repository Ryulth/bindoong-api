import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin

plugins {
    base
    id(Plugins.jvm) version Versions.kotlin
    id(Plugins.kapt) version Versions.kotlin
    id(Plugins.spring) version Versions.kotlin
    id(Plugins.jpa) version Versions.kotlin
    id(Plugins.springBoot) version Versions.springBoot
    id(Plugins.springDependencyManagement) version Versions.springDependencyManagement
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

        plugin(Plugins.jvm)
        plugin(Plugins.kapt)
        plugin(Plugins.spring)
        plugin(Plugins.jpa)
        plugin(Plugins.springBoot)
        plugin(Plugins.springDependencyManagement)
    }

    dependencies {
        api(Libs.Kotlin.stdlibJdk8)
        api(Libs.Kotlin.reflect)
        api(Libs.Kotlin.coroutinesCore)
        api(Libs.Kotlin.coroutinesJdk8)
        api(Libs.Kotlin.coroutinesReactor)
        api(Libs.Kotlin.logging)
    }

    tasks {
        compileJava {
            sourceCompatibility = Versions.jvm
            targetCompatibility = Versions.jvm
        }

        compileKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = Versions.jvm
            }
        }

        compileTestKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = Versions.jvm
            }
        }
    }
}
