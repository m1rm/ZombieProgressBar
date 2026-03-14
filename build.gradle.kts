plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.13.0"
}

group = "com.shushiro"
version = "1.1.0"

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
            untilBuild.set(provider { null })
        }
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
