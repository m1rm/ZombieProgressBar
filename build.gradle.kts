plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.19.0"
}

group = "com.m1rm"
version = "1.2.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdea("2025.2.6.1")
    }
}

intellijPlatform {
    pluginConfiguration {
        vendor {
            name.set("Miriam Müller")
            url.set("https://github.com/m1rm")
        }
        ideaVersion {
            // sinceBuild defaults to the target platform's build (252 = 2025.2)
            untilBuild.set(providers.provider { null })
        }
        changeNotes.set(
            """
            <h3>1.2.0</h3>
            <ul>
              <li>Fix: the zombie is drawn on top of the filled bar instead of behind it.</li>
              <li>Fix: uninstalling or updating the plugin restores the default progress bar and releases all plugin classes.</li>
              <li>Fix: installing the plugin switches already visible progress bars to the zombie immediately.</li>
              <li>Cleanup: removed unused code and the Guava cache dependency.</li>
              <li>Compatibility: requires JetBrains IDEs 2025.2 or later. Older IDEs keep version 1.1.2.</li>
              <li>Build: IntelliJ Platform Gradle Plugin 2.19, Gradle 9.8, Java 21.</li>
            </ul>
            <h3>1.1.2</h3>
            <ul>
              <li>Compatibility: No until-build ceiling; supports the latest JetBrains IDEs.</li>
              <li>Build against IntelliJ Platform 2025.2; vendor metadata set in Gradle for Marketplace consistency.</li>
            </ul>
            <h3>1.1.1</h3>
            <ul>
              <li>Compatibility: Fix incompatibility warning in Marketplace.</li>
              <li>UX: Fix preview image.</li>
            </ul>
            <h3>1.1.0</h3>
            <ul>
              <li>Fixed zombie position: no more cut-off head; feet sit on the progress bar.</li>
              <li>Compatibility: IntelliJ and JetBrains IDEs 2022.3 and later.</li>
              <li>Build: IntelliJ Platform Gradle Plugin 2.x, Gradle 9, Java 17.</li>
            </ul>
            """.trimIndent()
        )
    }
    // Publishing token and signing credentials are read from the PUBLISH_TOKEN,
    // CERTIFICATE_CHAIN, PRIVATE_KEY and PRIVATE_KEY_PASSWORD environment variables by default.
}
