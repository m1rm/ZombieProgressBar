# ZombieProgressBar ![Zombie Progress Bar Gif](src/main/resources/28x28_zombie.gif?raw=true "ZombieProgressBar")


![Zombie Progress Bar Screenshot](src/main/resources/barScreenshot?raw=true "ZombieProgressBar")

My take on a lovely zombie progress bar for IntelliJ and IntelliJ based IDEs.  

**Compatibility:** IntelliJ IDEA and other JetBrains IDEs 2022.3 and later.

## Build & Publish

- **Requirements:** JDK 17+, Gradle 8.13+ (wrapper included).
- **Build plugin (locally):** `./gradlew buildPlugin` — output: `build/distributions/ZombieProgressBar-1.0.0.zip`.
- **Run in IDE (locally):** `./gradlew runIde` to launch a sandbox IDE with the plugin installed.
- **Publish to [JetBrains Marketplace](https://plugins.jetbrains.com) (locally):**  
  Set `PUBLISH_TOKEN` (and for signing: `CERTIFICATE_CHAIN`, `PRIVATE_KEY`, `PRIVATE_KEY_PASSWORD`), then run `./gradlew publishPlugin`.

### Using Docker + just (no local Java/Gradle)

- **Build plugin:** `just build`
- **Run sandbox IDE:** `just run-ide` (requires host X11/GUI to be Docker-accessible).
- **Publish:**  
  Export `PUBLISH_TOKEN` (and optional signing env vars) on the host, then run `just publish`.

The IntelliJ Platform Gradle Plugin also creates a local cache folder named `.intellijPlatform/` in the project root. It is safe to delete and is ignored by Git.

## Contributions
Contributions are very welcome on this project! Contributions can take the form of bug reports, feature requests, zombie/survivor requests or more!

## Acknowledgements
Made possible by and based on: 
- [Law Millenium](https://github.com/law-millenium)'s great [Naruto Progress Bar](https://github.com/law-millenium/naruto-progress)
- [Nyan Cat Plugin](https://github.com/batya239/NyanProgressBar) by Dmitry Batkovich.
