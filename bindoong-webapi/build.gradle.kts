dependencies {
    api(project(":bindoong-core"))

    implementation(Libs.SpringBoot.webflux)
    implementation(Libs.SpringBoot.actuator)
    implementation(Libs.SpringBoot.security)
    implementation(Libs.Auth0.javaJwt)
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    testImplementation(Libs.SpringBoot.test) {
        exclude(module = "junit")
        exclude(module = "junit-vintage-engine")
    }
}