<h1 align="center">contractors</h1>
<h3 align="center">🐘  turn villagers into powerful city builders</h3>

<p align="center">
  <a href="https://jdk.java.net/">
    <img src="https://img.shields.io/badge/java-10.0.2-red.svg" />
  </a>
  
  <a href="https://getbukkit.org/download/craftbukkit">
    <img src="https://img.shields.io/badge/bukkit-1.13.2-orange.svg" />
  </a>
  
  <a href="https://www.spigotmc.org/resources/">
    <img src="https://img.shields.io/badge/🚀-Download%20on%20spigotmc.org-blue.svg" />
  </a>
</p>

## Building

Contractors was not built using Eclipse or any other IDE. Although you can use one, the instructions below are for building and running this plugin with only the command line and a few easy to understand tools.

### Requirements

- [Make](https://www.gnu.org/software/make/#download) to run the [makefile](https://github.com/insanj/contractors/blob/master/makefile), although each command inside this file can be ran on its own
- [spigot](https://getbukkit.org/download/spigot)`.jar` or `bukkit.jar` version **1.13.2** or above, currently expected in a local `external/` directory for building, and a `server/` directory for running
- [craftbukkit](https://getbukkit.org/download) expected in `external/` in order to parse schematic files
- [Java](https://www.oracle.com/technetwork/java/javase/downloads/index.html) 10 or above, required for `javac` and `jar` to run

> NOTE: Make sure the Java version on your server and the Java version on the machine that builds Contractors are the same, otherwise Bukkit/Spitgot will not allow it to run.

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
- https://www.spigotmc.org/threads/load-schematic-file-and-paste-at-custom-location.167277/
- https://bukkit.org/threads/pasting-loading-schematics.87129/
- https://www.spigotmc.org/threads/converting-item-id-and-damage-value-to-new-material-type.329614/

## Authors

```
Julian Weiss & Anna Raykovska
me@insanj.com
github.com/insanj
```

## License

See [LICENSE](https://github.com/insanj/contractors/blob/master/LICENSE). (c) 2019 Julian Weiss.

