import me.coderfrish.util.TomlUtil

plugins {
    kotlin("jvm") version "2.0.10"
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val config = TomlUtil(layout.projectDirectory.file("gradle.toml").asFile)

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

val apiVersion: String = config.getString("paper.version")

dependencies {
    paperweight.paperDevBundle(apiVersion)

    compileOnly("io.papermc.paper:paper-api:${apiVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.10") {
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation("org.jetbrains:annotations:24.1.0")

    implementation("org.reflections:reflections:0.10.2") {
        exclude(group = "org.slf4j", module = "slf4j-api")
        exclude(group = "org.slf4j", module = "slf4j-simple")
        exclude(group = "com.google.code.gson", module = "gson")
    }

    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.moandjiezana.toml:toml4j:0.7.2") {
        exclude("com.google.code.gson", "gson")
    }

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.assemble {
    dependsOn(tasks.reobfJar)
}

tasks.processResources {
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(
            mapOf(
                "pluginVersion" to config.getString("project.version"),
                "pluginMain" to config.getString("plugin.mainClass"),
                "pluginName" to config.getString("project.name"),
                "pluginDescription" to config.getString("project.description"),
                "pluginAuthor" to config.getString("project.author")
            )
        )
    }
}

val debug: Boolean = config.getBoolean("develop.debug")

tasks.shadowJar {
    destinationDirectory = if (debug) {
        File(rootDir, "run/plugins")
    } else {
        File(rootDir, "target")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
