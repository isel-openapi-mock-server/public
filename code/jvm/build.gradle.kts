
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
}

group = "isel.openapi.mock"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.swagger.parser.v3:swagger-parser:2.1.16") //Swagger parser
    implementation("commons-io:commons-io:2.15.0")  //Forçar uso de uma versao mais recente. Vulnerabilidade na versão usados por defeito pelo swagger parser
    implementation("com.google.guava:guava:32.1.2-jre") //Forçar uso de uma versao mais recente. Vulnerabilidade na versão usados por defeito pelo swagger parser
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.core)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
