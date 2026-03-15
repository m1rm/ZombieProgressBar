# ZombieProgressBar

## Preview

![Zombie progress bar in action](src/main/resources/barScreenshot "ZombieProgressBar")

My take on a lovely zombie progress bar for IntelliJ and IntelliJ based IDEs.  

**Compatibility:** IntelliJ IDEA and other JetBrains IDEs 2022.3 and later.

## Installation

Get it via the Jetbrains Marketplace: https://plugins.jetbrains.com/plugin/30702-zombieprogressbar

### Build Locally Using Docker + just (no local Java/Gradle)
**Requirements:**
- [docker compose](https://docs.docker.com/compose/)
- [just](https://github.com/casey/just)


- **Build plugin:** `just build`
- the Plugin .zip is available at build/distributions
- open your Jetbrains IDE: settings -> Plugins -> Cog -> Install  from Disk -> select the .zip Folder -> restart IDE

The IntelliJ Platform Gradle Plugin also creates a local cache folder named `.intellijPlatform/` in the project root. It is safe to delete and is ignored by Git.

## Contributions
Contributions are very welcome on this project! Contributions can take the form of bug reports, feature requests, zombie/survivor requests or more!

## Acknowledgements
Inspired by: 
- [Law Millenium](https://github.com/law-millenium)'s great [Naruto Progress Bar](https://github.com/law-millenium/naruto-progress)
- [Nyan Cat Plugin](https://github.com/batya239/NyanProgressBar) by Dmitry Batkovich.
