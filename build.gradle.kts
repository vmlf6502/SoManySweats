import dev.architectury.pack200.java.Pack200Adapter
import net.fabricmc.loom.task.RemapJarTask
import org.apache.commons.lang3.SystemUtils

plugins {
    idea
    java
    id("gg.essential.loom") version "0.10.0.+"
    id("dev.architectury.architectury-pack200") version "0.1.3"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val baseGroup = "com.replaymod"
val modid = "replaymod"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

loom {
//    log4jConfigs.from(file("log4j2.xml"))
    launchConfigs {
        "client" {
            arg("--tweakClass", "com.replaymod.core.tweaker.ReplayModTweaker")
            arg("--tweakClass", "io.github.notenoughupdates.moulconfig.tweaker.DevelopmentResourceTweaker")
        }
    }
    runConfigs {
        "client" {
            if (SystemUtils.IS_OS_MAC_OSX) {
                vmArgs.remove("-XstartOnFirstThread")
            }
        }
        remove(getByName("server"))
    }
    forge {
        pack200Provider.set(Pack200Adapter())
    }
}

sourceSets.main {
    output.setResourcesDir(sourceSets.main.flatMap { it.java.classesDirectory })
}

repositories {
    mavenCentral()
    maven("https://maven.notenoughupdates.org/releases/")
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
}

val shadowImpl: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}

val devEnv by configurations.creating {
    configurations.runtimeClasspath.get().extendsFrom(this)
}

dependencies {
    minecraft("com.mojang:minecraft:1.8.9")
    mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
    "forge"("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")

    compileOnly(files("ReplayMod-v1_8-2.6.14.jar"))
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("org.json:json:20240303")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
    shadowImpl("org.notenoughupdates.moulconfig:legacy:4.6.0")

    runtimeOnly("me.djtheredstoner:DevAuth-forge-legacy:1.2.1")
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}

tasks.withType(org.gradle.jvm.tasks.Jar::class) {
    archiveBaseName.set("ReplayMod-v1_8-2.6.14")
    manifest.attributes.run {
        this["TweakClass"] = "com.replaymod.core.tweaker.ReplayModTweaker"
        this["TweakOrder"] = "0"
        this["FMLCorePluginContainsFMLMod"] = "true"
        this["FMLCorePlugin"] = "com.replaymod.core.LoadingPlugin"
        this["FMLAT"] = "replaymod_at.cfg"
    }
}

tasks.processResources {
    filesMatching("mcmod.info") {
        expand("version" to project.version, "mcversion" to "1.8.9")
    }
}

val remapJar by tasks.named<RemapJarTask>("remapJar") {
    archiveClassifier.set("")
    from(tasks.shadowJar)
    input.set(tasks.shadowJar.get().archiveFile)
}

tasks.jar {
    archiveClassifier.set("without-deps")
    destinationDirectory.set(layout.buildDirectory.dir("intermediates"))
}

tasks.compileJava {
    doLast {
        copy {
            from(zipTree("ReplayMod-v1_8-2.6.14.jar"))
            exclude("com/replaymod/core/ReplayModBackend.class")
            into(sourceSets.main.get().output.classesDirs.first())
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
}

tasks.shadowJar {
    destinationDirectory.set(layout.buildDirectory.dir("intermediates"))
    archiveClassifier.set("non-obfuscated-with-deps")
    configurations = listOf(shadowImpl)
    mergeServiceFiles()
    fun relocate(name: String) = relocate(name, "$baseGroup.deps.$name")
    relocate("io.github.notenoughupdates.moulconfig")
}

// Copy into overrides/ folder automatically
tasks.build {
    finalizedBy("copyJar")
}
val homeDir: String? = System.getProperty("user.home")
tasks.register<Copy>("copyJar") {
    from(tasks.shadowJar)
    into("$homeDir/.lunarclient/offline/multiver/overrides") // should work for other OS's (untested)
    rename { "ReplayMod-v1_8-2.6.14.jar" }
}

tasks.assemble.get().dependsOn(tasks.remapJar)