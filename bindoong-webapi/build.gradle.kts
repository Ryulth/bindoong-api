dependencies {
    implementation(Libs.SpringBoot.webflux)
    implementation(Libs.SpringBoot.actuator)
    implementation(Libs.SpringBoot.security)
    implementation(Libs.Auth0.javaJwt)

    testImplementation(Libs.SpringBoot.test) {
        exclude(module = "junit")
        exclude(module = "junit-vintage-engine")
    }
}