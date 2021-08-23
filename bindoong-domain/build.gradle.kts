dependencies {
    api(project(":bindoong-core"))

    api(Libs.Spring.autoconfigure)
    api(Libs.Spring.relational)
    api(Libs.UUID.generator)
    implementation("com.github.f4b6a3:ulid-creator:4.0.0")
}
