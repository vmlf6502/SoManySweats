<!-- markdownlint-disable-file MD001 MD033 -->
<h1 align="center">SoManySweats</h1>

SoManySweats is a Minecraft 1.8.9 Forge mod designed for Lunar Client that lets you view Hypixel Bedwars player stats directly in-game, via the tab list.

<img width="1920" height="1080" alt="SoManySweats_showcase" src="https://github.com/user-attachments/assets/c8077c64-610f-43b0-aa6b-458f0931f57e" />

## Features
- Displays stats of players in tab, making it easy to match stats with teams
- Stats divided into organised columns on the tab list
- Color coded stats for every prestige up to 5000 stars
- Automatically fetches stats at the start of a Bedwars game

## Getting Started

### Installation - Lunar Client

1. If you don't already have it, download and install [Lunar Client](https://www.lunarclient.com/)
2. Download the [latest version of SoManySweats](https://github.com/vmlf6502/SoManySweats/releases)
3. Press `Windows + R` to open Run.
4. Paste this path in the text box and hit `Enter`
    * `%USERPROFILE%\.lunarclient\offline\multiver\overrides`
5. Place the downloaded `.jar` file in the `overrides` folder you just opened
6. If it isn't already, name the `.jar` file EXACTLY this `ReplayMod-v1_8-2.6.14.jar`
7. Launch the game through Lunar Client and run the command `/sms`.
   If you see a nice GUI pop up, then you have successfully installed SoManySweats!
   Go ahead and get an API key from the [Hypixel Developer Dashboard](https://developer.hypixel.net/dashboard) to start using the mod. You may need to log in or create an account.

### Installation - Other Forge Clients

1. While this mod was designed for Lunar Client, it can also work with other clients such as Feather Client or Labymod.
2. Download the [latest version of SoManySweats](https://github.com/vmlf6502/SoManySweats/releases)
3. Find the mods folder for your specific client.
4. Place the downloaded `.jar` file in the `mods` folder
5. Launch the game and run the command `/sms`.
   If you see a nice GUI pop up, then you have successfully installed SoManySweats!
   Go ahead and get an API key from the [Hypixel Developer Dashboard](https://developer.hypixel.net/dashboard) to start using the mod. You may need to log in or create an account.

### How to Use

- `/sms` - Opens a GUI where you can configure your API key
    * API keys can be found on the [Hypixel Developer Dashboard](https://developer.hypixel.net/dashboard)
    * Run the command, then navigate to the `API` category on the left. Paste your API key into the text box and you should be good to go!
- `/sms fetch` - Fetch the stats of the players in your game.
    * Stats will appear in the tab list
- `/sms clear` - Clear the local stats cache so that you can request them from the API again.
    * This does not fetch any new stats; you still have to run `/sms fetch` to do that

### Helpful Tips
If the stats in tab aren't perfectly aligned in nice, neat columns, here are some things you can do to fix it:
- On Lunar Client, enable the Tab Editor mod and turn on the option to `Show Lunar Icons on Right`.
  This removes the offset produced by the Lunar Client icon seen next to Lunar users' names on tab.
  Optionally, you can remove the Lunar icon feature entirely.
- Use the default Minecraft font. This is going to help you out the most, and it can be done in a variety of ways:
  1. Don't use a resource pack. This is the easiest option, but is probably the least favorable for most people.
  2. Find a resource pack that doesn't have a custom font or uses a font with the same spacings as the default font.
  3. Delete the `fonts` folder from your resource pack of choice. This causes Minecraft to use its own default font.
  4. Make your own resource pack that just contains the default font and load it over your other resource packs.
  This involves quite a bit of setup, but once it's done, it works everywhere.
        <details>
        <summary>Click here for specific steps</summary>
  
     1. Press `Windows + R` to open Run.
     2. Paste this path in the text box and hit `Enter`
        * `%APPDATA%\.minecraft\versions\1.8.9`
     3. Right click on `1.8.9.jar` > `Open with` > Any kind of unzipping software (I use BreeZip, but you can use whatever)
     4. Navigate to `assets/minecraft/textures/` and copy the `font` folder
     5. Open your `resourcepacks` folder through the in-game `Resource Packs` options menu
     6. Download the `SMSResourcePack.zip` from the [latest release](https://github.com/vmlf6502/SoManySweats/releases) and move it into your `resourcepacks` folder
     7. Inside the resource pack, navigate to `assets/minecraft/textures/` and paste the `font` folder you just copied
     8. Now, in game, you should be able to use the `SMSResourcePack`.
     Be sure to put it on top of all your other resource packs or else it won't actually load.
        </details>

## How It Works
Lunar Client has this handy feature that allows you to override third-party mods that it uses.
As it happens, Lunar Client uses a popular third party open-source mod called [ReplayMod](https://github.com/ReplayMod/ReplayMod) (go check them out btw).
So, we can modify ReplayMod as much as we want (as long as it still contains all the classes of the original ReplayMod), and then override Lunar Client's version of SoManySweats with our own custom version.
More information on this can be found at [this article](https://support.lunarclient.com/support/solutions/articles/60000752051-third-party-mods) on Lunar Client's website. Because this still
works as a normal Forge mod, you are able to use this with other clients such as Feather Client or Labymod.
Note: this is actually a partial decompilation of a 1.8.9 build of ReplayMod because I wasn't able to get ReplayMod's preprocessor working.
A 1.8.9 build of ReplayMod is included in the source, and the `ReplayModBackend` class is overridden as a way to hook into the Forge initialization process.

## Important Notice
This project uses [ReplayMod](https://github.com/ReplayMod/ReplayMod) as a base in order to get its own code running inside Lunar Client.
I do NOT own nor contribute to ReplayMod. All ReplayMod code is still licensed to them, and SoManySweats inherits the GPL-3.0 license from them.
Their README can be found at `-README.md` in the root of this project.
Huge thanks to ReplayMod for making this project possible.

Also, big thank you to [axlecoffee](https://github.com/axlecoffee) for making a super cool mod called [CoffeeClient](https://github.com/axlecoffee/CoffeeClient), which is what inspired me to make this.
Without it, I wouldn't have figured out how to get custom code running inside Lunar Client, and this project wouldn't exist!
