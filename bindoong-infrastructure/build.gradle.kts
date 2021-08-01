dependencies {
    api(project(":bindoong-core"))
    api(project(":bindoong-domain"))

    implementation(Libs.SpringBoot.autoconfigure)
    implementation(Libs.SpringData.r2dbc)
    implementation(Libs.SpringData.r2dbcMysql)
}