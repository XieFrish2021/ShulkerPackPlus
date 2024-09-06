plugins {
    java
}

repositories {
    google()
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.moandjiezana.toml:toml4j:0.7.2") {
        exclude("com.google.code.gson", "gson")
    }
}
