dependencies {
    api(project(":bindoong-core"))

    implementation(Libs.Spring.autoconfigure)
    implementation(Libs.Spring.relational)
    implementation(Libs.Generator.uuid)
    implementation(Libs.Generator.ulid)
}
