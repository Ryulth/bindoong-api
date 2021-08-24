dependencies {
    api(project(":bindoong-core"))
    api(project(":bindoong-domain"))
    api(project(":bindoong-infrastructure"))

    implementation(Libs.Spring.autoconfigure)
    implementation(Libs.Spring.relational)
}
