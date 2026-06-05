# Contributing

To contribute, you can fork this repository on GitHub and submit a Pull Request.
If you don't know how to create a Pull Request or even what they are, don't worry.
I won't bother writing a tutorial for that, but you can find several good tutorials out there to learn from.

This guide assumes you are using IntelliJ IDEA, however, you can use your code editor of choice. The steps will probably just be different.

## Setup
Inside IntelliJ, at the top bar, go to `File > New > Project from Version Control`.
Then, paste the URL of your fork in the text box.
Optionally, you can check the box to shallow clone to 1 commit, which will save bandwidth and storage space at the sacrifice of an incomplete Git history (you can unshallow your project later if you choose).
Once the window opens click the settings icon in the top right and navigate to `Settings > Build, Execution, and Deployment > Build Tools > Gradle` and set the Gradle JVM to version 17.
Once that's done, click the settings icon again and find to Project Structure.
There, set the SDK to version 1.8 and make sure that Language Level is set to SDK Default.

## Building
The easiest way to build the mod is to click on the Gradle icon in the right toolbar, navigate to `SoManySweats > Tasks > build`, and double-click on the *build* module.
You can assign a keyboard shortcut to this action by right-clicking on the build module and pressing Assign Shortcut, right-clicking on the build module in the window that just popped up, and finally clicking Add Keyboard Shortcut.
I personally use Alt + B for this, as it's not used by any default keyboard shortcuts.
Optionally, you can use the CLI to build the mod using `./gradlew build`. Just remember to set your JAVA_HOME to JDK 1.8 if you're on Windows (I pity you), or however else you set your Java version on your OS.

## Notes
In order for Lunar Client to load your mod, it must be named **exactly** `ReplayMod-v1_8-2.6.14.jar` (or whatever version Lunar Client uses at the time) and be placed **exactly** in `~/.lunarclient/offline/multiver/overrides/`.
By default, the built `.jar` is automatically placed in that folder so you don't have to do that manually (works on Windows and Linux for sure, but I don't know about macOS).
Additionally, the built `.jar` can also be found in the `build/libs/` folder in the root of your project.
The `ReplayMod-v1_8-2.6.14.jar` in the root of the project is NOT the built `.jar`.
That is the vanilla version of ReplayMod that we get all the ReplayMod classes from.

## Help
If you run into issues, you can open an issue on GitHub, and we'll help you as soon as we can.