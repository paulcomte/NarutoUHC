# NarutoUHC by RqndomHax

## Getting Started

You server need to run `Spigot-1.8.8.jar`.

# Installation

Build the sources with maven and copy the `NarutoUHC.jar` to your `plugins` server's folder.

# Prerequiresites

Download the `NarutoUHC` map here: https://www.planetminecraft.com/project/naruto-shinobi-legend-s-map/
Create a `original` folder in your server's root.

```
...
plugins/
  NarutoUHC.jar
original/
server.jar
server.properties
...
```

Extract the map's archive into the `original` folder, and rename it `NARUTO_UNIVERSE`.

```
...
plugins/
  NarutoUHC.jar
original/
  NARUTO_UNIVERSE/
server.jar
server.properties
...
```

# Run your server

## Commands

# Console

### `/host set <player>`
This command sets the `<player>` as the game's host, the original game's host needs to be disconnected.

# Host

### `/host kick <player>`
This command kicks the `<player>` from the game.

### `/host ban <player>`
This command bans the `<player>` from the game.

### `/host unban <player>`
This commands cancel the ban of the `<player>`.

### `/host promote <player>`
This command promotes the `<player>` as a co-host of the game.

### `/host delete <player>`
This command demotes the `<player>` from co-host of the game.

# Host - Co-Host

### `/heal`
This command heals all the players.

### `/revive <player>`
This command revives the `<player>` with his inventory.

### `/host say <message>`
This command broadcasts a `<message>`.