plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.13.0"
}

group = "com.m1rm"
version = "1.1.1"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdea("2022.3")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild.set("223")
            untilBuild.set("253.*")
        }
        changeNotes.set(
            """
            <h3>1.1.0</h3>
            <ul>
              <li>Fixed zombie position: no more cut-off head; feet sit on the progress bar.</li>
              <li>Compatibility: IntelliJ and JetBrains IDEs 2022.3 and later.</li>
              <li>Build: IntelliJ Platform Gradle Plugin 2.x, Gradle 9, Java 17.</li>
            </ul>
            <h3>1.1.1</h3>
            <ul>
              <li>Compatibility: Fix incompatibility warning in Marketplace.</li>
              <li>UX: Fix preview image.</li>
            </ul>
            """.trimIndent()
        )
    }
    publishing {
        token.set(providers.environmentVariable("PUBLISH_TOKEN"))
    }
    signing {
        certificateChain.set(providers.environmentVariable("CERTIFICATE_CHAIN"))
        privateKey.set(providers.environmentVariable("PRIVATE_KEY"))
        password.set(providers.environmentVariable("PRIVATE_KEY_PASSWORD"))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(17)
}
