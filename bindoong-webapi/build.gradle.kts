dependencies {
    api(project(":bindoong-core"))
    api(project(":bindoong-service"))
    api(project(":bindoong-infrastructure"))

    implementation(Libs.Spring.webflux)
    implementation(Libs.Spring.actuator)
    implementation(Libs.Spring.security)
    implementation(Libs.Auth0.javaJwt)
    implementation(Libs.SpringFox.starter)
    implementation(Libs.SpringFox.swagger2)
    implementation(Libs.SpringFox.ui)
    implementation(Libs.SpringFox.webflux)
    testImplementation(Libs.Spring.test) {
        exclude(module = "junit")
        exclude(module = "junit-vintage-engine")
    }
}
