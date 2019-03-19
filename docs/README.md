<h1 align="center">mayor</h1>
<h3 align="center">🐘  turn villagers into powerful city builders</h3>

<p align="center">
  <a href="https://github.com/insanj/mayor/releases">
    <img src="https://img.shields.io/github/release/insanj/mayor.svg" />
    <img src="https://img.shields.io/github/release-date/insanj/mayor.svg" />
  </a>

  <a href="https://github.com/insanj/mayor/">
    <img src="https://img.shields.io/github/languages/code-size/insanj/mayor.svg" />
  </a>

  <br/>

  <a href="https://github.com/insanj/mayor/blob/master/LICENSE">
    <img src="https://img.shields.io/github/license/insanj/mayor.svg" />
  </a>

  <a href="https://jdk.java.net/">
    <img src="https://img.shields.io/badge/java-8-yellow.svg" />
  </a>

  <a href="https://getbukkit.org/download/craftbukkit">
    <img src="https://img.shields.io/badge/minecraft-1.13-purple.svg" />
  </a>

  <a href="https://getbukkit.org/download/craftbukkit">
    <img src="https://img.shields.io/badge/craftbukkit-v1_13_R2-blue.svg" />
  </a>

  <a href="https://github.com/insanj/mayor/releases">
    <img src="https://img.shields.io/badge/🚀-Download%20on%20Github-red.svg" />
  </a>
</p>

## Building

Mayor was not built using Eclipse or any other IDE. Although you can use one, the instructions below are for building and running this plugin with only the command line and a few easy to understand tools.

### Requirements

#### Tools
- [make](https://www.gnu.org/software/make/#download) to run the [makefile](https://github.com/insanj/mayor/blob/master/makefile)
- [Java](https://www.oracle.com/technetwork/java/javase/downloads/index.html) 10 or above, required for `javac` and `jar` to run

> NOTE: Make sure the Java version on your server and the Java version on the machine that builds Mayor are the same, otherwise Bukkit/Spitgot will not allow it to run.

#### JARs
- [spigot.jar](https://getbukkit.org/download/spigot) version **1.13** or above
- [craftbukkit.jar](https://getbukkit.org/download) version **1.13** or above

> NOTE: These files are expected in a folder called `external` that you should create within the repo after cloning. In addition, the `spigot.jar` will be used to launch a server in an expected `server` directory. Both these features are easy to understand and configure within the [makefile](https://github.com/insanj/mayor/blob/master/makefile).

### Commands

#### `make plugin`
- cleans `build/`
- compiles the `plugin/` directory
- builds a `.jar`
- moves it into `server/plugins/` for use in the Spigot/Bukkit server

#### `make server`
- launches the server

---

## Thanks

A lot of important work parsing and building structures from .schematic/NBT files was made possible by open source contributors on Bukkit and Spigot forums. In particular:
- https://github.com/Shynixn/StructureBlockLib
- https://www.spigotmc.org/threads/load-schematic-file-and-paste-at-custom-location.167277/
- https://bukkit.org/threads/pasting-loading-schematics.87129/
- https://www.spigotmc.org/threads/converting-item-id-and-damage-value-to-new-material-type.329614/
- https://bukkit.org/threads/so-block-setdata-is-deprecated-whats-the-new-way-to-change-the-data-of-an-existing-block.189076/
- https://www.spigotmc.org/threads/tutorial-example-handle-structures.331243/

## Authors

(c) 2019 Julian Weiss. <a href="http://insanj.com">insanj.com</a>

## License

See [LICENSE](https://github.com/insanj/mayor/blob/master/LICENSE). (c) 2019 Julian Weiss.
