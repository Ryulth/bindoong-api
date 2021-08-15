dependencies {
    api(project(":bindoong-core"))
    api(project(":bindoong-domain"))

    api(Libs.Spring.autoconfigure)
    api(Libs.Spring.webflux)
    api(Libs.Spring.r2dbc)
    api(Libs.Spring.r2dbcMysql)
    api(Libs.Spring.jdbcMysql)
    api(Libs.Flyway.core)
    api(Libs.Aws.sts)
    api(Libs.Aws.s3)
    api(Libs.Aws.nettyNioClient)
    api(Libs.Apache.commonsIo)
}
