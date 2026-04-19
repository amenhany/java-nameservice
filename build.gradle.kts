plugins {
    id("java")
    id("application")
}

group = "org.nameservice"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("runServer") {
    group = "application"
    mainClass.set("org.nameservice.server.NameServiceServer")
    classpath = sourceSets.main.get().runtimeClasspath
    args("3000")
}

tasks.register<JavaExec>("runClient") {
    group = "application"
    mainClass.set("org.nameservice.client.NodeClient")
    classpath = sourceSets.main.get().runtimeClasspath
    args("localhost", "3000")
}
