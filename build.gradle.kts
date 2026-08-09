import dev.architectury.pack200.java.Pack200Adapter
import net.fabricmc.loom.task.RemapJarTask
import org.apache.commons.lang3.SystemUtils

plugins {
    idea
    java
    id("gg.essential.loom") version "0.10.0.5"
    id("dev.architectury.architectury-pack200") version "0.1.3"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

version = "0.3.3"

// -Pprofile=lunar to build for Lunar Client
val buildProfile = project.findProperty("profile")?.toString() ?: "standalone"
val isLunar = buildProfile == "lunar"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

loom {
    launchConfigs {
        "client" {
            if (isLunar) {
                arg("--tweakClass", "com.replaymod.core.tweaker.ReplayModTweaker")
            }
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

val lunarBackend: SourceSet = sourceSets.create("lunarBackend")
lunarBackend.java.setSrcDirs(listOf("src/lunar/java"))
lunarBackend.compileClasspath += sourceSets.main.get().compileClasspath + sourceSets.main.get().output

val standaloneBackend: SourceSet = sourceSets.create("standaloneBackend")
standaloneBackend.java.setSrcDirs(listOf("src/standalone/java"))
standaloneBackend.compileClasspath += sourceSets.main.get().compileClasspath + sourceSets.main.get().output

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

    if (isLunar) {
        runtimeOnly(lunarBackend.output)
    } else {
        runtimeOnly(standaloneBackend.output)
    }

    compileOnly(files("ReplayMod-v1_8-2.6.14.jar"))
    // lunarBackend also needs ReplayMod on its classpath to compile against
    "lunarBackendCompileOnly"(files("ReplayMod-v1_8-2.6.14.jar"))

    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.json:json:20240303")
    if (isLunar) {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
    } else {
        shadowImpl("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
    }
    shadowImpl("org.notenoughupdates.moulconfig:legacy:4.6.0")

    runtimeOnly("me.djtheredstoner:DevAuth-forge-legacy:1.2.1")
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}

tasks.withType(org.gradle.jvm.tasks.Jar::class) {
    archiveBaseName.set("SoManySweats")
    manifest.attributes.run {
        this["Implementation-Version"] = version
        if (isLunar) {
            this["TweakClass"] = "com.replaymod.core.tweaker.ReplayModTweaker"
            this["TweakOrder"] = "0"
            this["FMLCorePluginContainsFMLMod"] = "true"
            this["FMLCorePlugin"] = "com.replaymod.core.LoadingPlugin"
            this["FMLAT"] = "replaymod_at.cfg"
        }
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
            if (isLunar) {
                // Lunar: bundle all of ReplayMod except its own Backend (yours replaces it)
                exclude("com/replaymod/core/ReplayModBackend.class")
            } else {
                // Standalone: exclude all of ReplayMod — it's a provided dependency at runtime
                exclude("com/replaymod/**")
            }
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

    if (isLunar) {
        dependsOn(tasks.named("compileLunarBackendJava"))
        from(lunarBackend.output)
        relocate("me.vmlf6502.somanysweats", "com.replaymod.somanysweats")
        relocate("io.github.notenoughupdates.moulconfig", "com.replaymod.deps.moulconfig")
    } else {
        dependsOn(tasks.named("compileStandaloneBackendJava"))
        from(standaloneBackend.output)
        relocate("io.github.notenoughupdates.moulconfig", "me.vmlf6502.somanysweats.deps.moulconfig")
    }
}

// Copy into somanysweats/ folder automatically
if (isLunar) {
    tasks.build {
        finalizedBy("copyJar")
    }
    tasks.named("remapJar") {
        finalizedBy("copyJar")
    }
    val homeDir: String? = System.getProperty("user.home")
    tasks.register<Copy>("copyJar") {
        from(tasks.shadowJar)
        into("$homeDir/.lunarclient/offline/multiver/somanysweats")
    }
}

tasks.assemble.get().dependsOn(tasks.remapJar)