<!-- markdownlint-disable-file MD001 MD033 -->
<h1 align="center">SoManySweats</h1>

SoManySweats is a Minecraft 1.8.9 Forge mod designed for Lunar Client that lets you view Hypixel Bedwars player stats directly in-game, via the tab list.

<img width="1920" height="1080" alt="SoManySweats_showcase" src="https://github.com/user-attachments/assets/c8077c64-610f-43b0-aa6b-458f0931f57e" />

## ✨ Features
- Displays stats of players in tab, making it easy to match stats with teams
- Stats divided into organised columns on the tab list
- Color coded stats for every prestige up to 5000 stars
- Automatically fetches stats at the start of a Bedwars game

## 🔭 Roadmap
✅ = Completed, 🔧 = Work in Progress, ❌ = Not Started
| Status | Feature |
| --- | --- |
| <p align="center">✅</p> | Stats in organised columns |
| <p align="center">✅</p> | Color coded stats up to 5000 stars |
| <p align="center">🔧</p> | Game start detection using scoreboard |
| <p align="center">❌</p> | Display player stats when someone talks in chat before a game |
| <p align="center">❌</p> | Fix Lunar Client font issue |
| <p align="center">❌</p> | Add menu to look up any player's stats from in-game |

## 🚀 Getting Started

### 🌙 Installation - Lunar Client

1. If you don't already have it, download and install [Lunar Client](https://www.lunarclient.com/)
    * Be sure to launch Lunar Client with the Forge module at least once before installing SoManySweats
2. Download the [latest version of SoManySweats](https://github.com/vmlf6502/SoManySweats/releases)
3. Open whatever file explorer you use and paste in this path depending on your OS
    * 🪟🤢&nbsp;&nbsp;<b>Windows</b>
      ```
      %USERPROFILE%\.lunarclient\offline\multiver
      ```
    * 🐧✨&nbsp;&nbsp;<b>Linux</b>
      ```
      ~/.lunarclient/offline/multiver
      ```
5. Place the downloaded `.jar` file in the `somanysweats/` folder at that directory
    * If you don't have a `somanysweats/` folder, you can simply create one
6. You must run this command in the terminal before each launch, or else Lunar Client will not load SoManySweats.
   An app to facilitate this process is currently in the works, and it will be released soon.
    * 🪟🤢&nbsp;&nbsp;<b>Windows (PowerShell)</b>
      
      ```
      $log = "$env:USERPROFILE\.lunarclient\profiles\1.8\logs\ichor-boot.log"; $pos = (Get-Item $log).Length; while ($true) { $fs = [System.IO.FileStream]::new($log, [System.IO.FileMode]::Open, [System.IO.FileAccess]::Read, [System.IO.FileShare]::ReadWrite); $fs.Seek($pos, [System.IO.SeekOrigin]::Begin) | Out-Null; $reader = [System.IO.StreamReader]::new($fs); $content = $reader.ReadToEnd(); $reader.Close(); $fs.Close(); if ($content -match "LUNARCLIENT_STATUS_PREINIT") { $src = (Get-Item "$env:USERPROFILE\.lunarclient\offline\multiver\somanysweats\SoManySweats-*.jar").FullName; [System.IO.File]::Copy($src, "$env:USERPROFILE\.lunarclient\offline\multiver\ReplayMod-v1_8-2.6.14.jar", $true); break }; $pos = (Get-Item $log).Length; Start-Sleep -Milliseconds 200 }
      ```
    * 🐧✨&nbsp;&nbsp;<b>Linux</b></summary>

      ```
      tail -F ~/.lunarclient/profiles/1.8/logs/ichor-boot.log | grep --line-buffered -m1 "LUNARCLIENT_STATUS_PREINIT" && cp ~/.lunarclient/offline/multiver/somanysweats/SoManySweats-*.jar ~/.lunarclient/offline/multiver/ReplayMod-v1_8-2.6.14.jar
      ```
8. Launch the game through Lunar Client with the Forge module activated and run the command `/sms`.
    * If you see a nice GUI pop up, then you have successfully installed SoManySweats!

### 🔨 Installation - Other Forge Clients
While this mod was designed for Lunar Client, it can also work with other clients such as Feather Client, Modrinth, or just plain Forge.
Just install it on those platforms as you would any regular 1.8.9 mod. You'll just have ReplayMod bundled in as well.

### 📄 How to Use

- `/sms` - Opens a GUI where you can configure your SoManySweats settings.
- `/sms fetch` - Fetch the stats of the players in your game.
    * Stats will appear in the tab list
- `/sms clear` - Clear the local stats cache so that you can request them from the API again.
    * This does not fetch any new stats; you still have to run `/sms fetch` to do that

### 💡 Helpful Tips
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

## ⚙️ How It Works
Lunar Client recently removed the `overrides` method that let us easily sideload mods into the game (more info on this obsolete method can be found on [this deleted article by Lunar Client](https://web.archive.org/web/20260221030830/https://support.lunarclient.com/support/solutions/articles/60000752051-third-party-mods). However, I've found that **there exists a brief 2-3 second window after Lunar Client checks its resources but before it begins initializing Forge mods in which we can literally just overwrite the ReplayMod file and Lunar will still load it**. So, we can modify ReplayMod as much as we want (as long as it still contains all the classes of the original ReplayMod), and then overwrite Lunar Client's version of ReplayMod (within that 2-3 second window) with our own custom version.

## 🧑‍💻 Developer Mode
SoManySweats features a Developer Mode, which allows you to use your own API key instead of our Personal API key that is restricted to the `/player` endpoint of the Hypixel API.
This feature is intended for development purposes only, and any usage under this mode is solely the responsibility of the individual developer.
If you get banned from the Hypixel API when using Developer Mode, it is not our fault.
 * Development API keys can be found on the [Hypixel Developer Dashboard](https://developer.hypixel.net/dashboard). You may need to log in or create an account to get one.

## 👷 Contributing
Contributors are welcome! See the [contribution guide](https://github.com/vmlf6502/SoManySweats/blob/main/CONTRIBUTING.md) to learn how to contribute.

## ‼️ Important Notice
This project uses [ReplayMod](https://github.com/ReplayMod/ReplayMod) as a base in order to get its own code running inside Lunar Client.
I do NOT own nor contribute to ReplayMod. All ReplayMod code is still licensed to them, and SoManySweats inherits the GPL-3.0 license from them.
Their README can be found at `-README.md` in the root of this project.
Huge thanks to ReplayMod for making this project possible.

Disclaimer: This project is not affiliated with nor endorsed by Hypixel in any way. Hypixel is a trademark of Hypixel Inc.

Also, big thank you to [axlecoffee](https://github.com/axlecoffee) for making a super cool mod called [CoffeeClient](https://github.com/axlecoffee/CoffeeClient), which is what inspired me to make this.
Without it, I wouldn't have figured out how to get custom code running inside Lunar Client, and this project wouldn't exist!
