import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("plugin.serialization")
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "org.iesharia"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Compose
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation(compose.foundation)

    // Voyager
    implementation("cafe.adriel.voyager:voyager-navigator:${property("voyager.version")}")
    implementation("cafe.adriel.voyager:voyager-transitions:${property("voyager.version")}")
    implementation("cafe.adriel.voyager:voyager-koin:${property("voyager.version")}")

    // Ktor
    implementation("io.ktor:ktor-client-core:${property("ktor.version")}")
    implementation("io.ktor:ktor-client-cio:${property("ktor.version")}")
    implementation("io.ktor:ktor-client-content-negotiation:${property("ktor.version")}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${property("ktor.version")}")
    implementation("io.ktor:ktor-client-logging:${property("ktor.version")}")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${property("coroutines.version")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:${property("coroutines.version")}")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${property("serialization.version")}")

    // DateTime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:${property("datetime.version")}")

    // Koin
    implementation("io.insert-koin:koin-core:${property("koin.version")}")

    // Logging
    implementation("ch.qos.logback:logback-classic:${property("logback.version")}")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "GestionProyectoKMP"
            packageVersion = "1.0.0"
        }
    }
}
