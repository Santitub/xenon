import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.jpenilla.runpaper.task.RunServer

plugins {
    id("java-library")
    id("checkstyle")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("xyz.jpenilla.run-paper") version "2.2.3"
}

group = "dev.portero.xenon"
version = "0.8.6-ALPHA"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://repo.panda-lang.org/releases/") }
    maven { url = uri("https://repository.minecodes.pl/releases/") }
    maven { url = uri("https://repository.minecodes.pl/snapshots/") }
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
}

dependencies {
    // Testing
    testImplementation(
        "io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT"
    )
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")

    testImplementation("org.mockito:mockito-core:5.11.0")

    testImplementation("net.kyori:adventure-platform-bukkit:4.3.2")
    testImplementation("net.kyori:adventure-platform-facet:4.3.2")

    // Base libraries
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("io.papermc:paperlib:1.0.8")

    implementation("net.kyori:adventure-platform-bukkit:4.3.2")
    implementation("net.kyori:adventure-text-minimessage:4.16.0")

    bukkitLibrary("com.google.code.gson", "gson", "2.10.1")

    implementation("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    implementation("net.dzikoysk:cdn:1.14.4")
    implementation("commons-io:commons-io:2.16.0")

    implementation("com.j256.ormlite:ormlite-core:6.1")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")

    implementation("com.zaxxer:HikariCP:5.1.0")

    implementation("dev.rollczi:litecommands-bukkit:3.4.1")
    implementation("dev.rollczi:litecommands-adventure-platform:3.4.1")
    implementation("dev.rollczi:liteskullapi:1.3.0")

    implementation("me.clip:placeholderapi:2.11.5")

    implementation("dev.triumphteam:triumph-gui:3.1.7")
}

bukkit {
    main = "dev.portero.xenon.XenonPlugin"
    apiVersion = "1.20"
    prefix = "Xenon"
    name = "Xenon"
    version = "${project.version}"
    authors = listOf("Portero")
    description = "A simple RPG plugin for Minecraft"
    loadBefore = listOf("PlaceholderAPI", "LuckPerms")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:deprecation", "-parameters"))
}

configurations.named("checkstyle") {
    resolutionStrategy {
        capabilitiesResolution {
            withCapability("com.google.collections:google-collections") {
                select("com.google.guava:guava:33.1.0-jre")
            }
        }
    }
}

checkstyle {
    toolVersion = "10.15.0"

    configFile = file("${rootDir}/config/checkstyle/checkstyle.xml")
    configProperties["checkstyle.suppressions.file"] = "${rootDir}/config/checkstyle/suppressions.xml"

    maxErrors = 0
    maxWarnings = 0
}


tasks.withType<RunServer> {
    minecraftVersion("1.20.4")

    downloadPlugins {
        url("https://ci.extendedclip.com/job/PlaceholderAPI/lastSuccessfulBuild/artifact/build/libs/PlaceholderAPI-2.11.6-DEV-191.jar")
        url("https://ci.lucko.me/job/LuckPerms/lastSuccessfulBuild/artifact/bukkit/loader/build/libs/LuckPerms-Bukkit-5.4.121.jar")
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<ShadowJar> {
    dependsOn("checkstyleMain")
    dependsOn("checkstyleTest")
    dependsOn("test")

    archiveClassifier.set("")
    archiveFileName.set("Xenon-${project.version}.jar")
}
