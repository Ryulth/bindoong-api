dependencies {
    api(project(":bindoong-core"))
    api(project(":bindoong-service"))

    implementation(Libs.SpringBoot.webflux)
    implementation(Libs.SpringBoot.actuator)
    implementation(Libs.SpringBoot.security)
    implementation(Libs.Auth0.javaJwt)
    implementation(Libs.SpringFox.starter)
    testImplementation(Libs.SpringBoot.test) {
        exclude(module = "junit")
        exclude(module = "junit-vintage-engine")
    }
}