plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.13.1"
}

group = "com.m1rm"
version = "1.1.2"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        // Compile and run IDE against a current platform; runtime range is controlled by ideaVersion below.
        intellijIdea("2025.2.6.1")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

intellijPlatform {
    pluginConfiguration {
        vendor {
            name.set("Miriam Müller")
            url.set("https://github.com/m1rm")
        }
        ideaVersion {
            sinceBuild.set("223")
            // No upper bound — compatible with current and future IDE releases (see IPGP docs: untilBuild provider null).
            untilBuild.set(providers.provider { null })
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
            <h3>1.1.2</h3>
            <ul>
              <li>Compatibility: No until-build ceiling; supports the latest JetBrains IDEs.</li>
              <li>Build against IntelliJ Platform 2025.2; vendor metadata set in Gradle for Marketplace consistency.</li>
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
