plugins {
    kotlin("jvm") version "1.8.20"
    application
}

group = "com.pittacode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("it.skrape:skrapeit:1.2.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.5")



    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-assertions-core:5.5.4")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}