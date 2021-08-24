dependencies {
    api(project(":bindoong-core"))
    api(project(":bindoong-domain"))

    implementation(Libs.Spring.autoconfigure)
    implementation(Libs.Spring.webflux)
    implementation(Libs.Spring.r2dbc)
    implementation(Libs.Spring.r2dbcMysql)
    implementation(Libs.Spring.jdbcMysql)
    implementation(Libs.Flyway.core)
    implementation(Libs.Aws.sts)
    implementation(Libs.Aws.s3)
    implementation(Libs.Aws.nettyNioClient)
    implementation(Libs.Apache.commonsIo)
    implementation(Libs.Generator.uuid)
}
