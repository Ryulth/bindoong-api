dependencies {
    api(project(":bindoong-core"))
    api(project(":bindoong-domain"))

    api(Libs.Spring.autoconfigure)
    api(Libs.Spring.r2dbc)
    api(Libs.Spring.r2dbcMysql)
    api(Libs.Flyway.core)
    api(Libs.Spring.jdbcMysql)
}
