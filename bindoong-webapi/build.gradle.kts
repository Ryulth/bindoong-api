dependencies {
    api(project(":bindoong-core"))
    api(project(":bindoong-service"))
    api(project(":bindoong-infrastructure"))

    api(Libs.Spring.webflux)
    api(Libs.Spring.actuator)
    api(Libs.Spring.security)
    api(Libs.Auth0.javaJwt)
    api(Libs.SpringFox.starter)
    testApi(Libs.Spring.test) {
        exclude(module = "junit")
        exclude(module = "junit-vintage-engine")
    }
}
