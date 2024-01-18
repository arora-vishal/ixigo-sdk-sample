pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://nexus.ixigo.com/nexus/content/repositories/androidshared") }
        maven {
            url = uri("https://jitpack.io")
        }
        maven {
            url = uri("https://nexus.ixigo.com/nexus/content/repositories/androidshared")
        }
        maven {
            url = uri("https://nexus.ixigo.com/nexus/content/repositories/androidshared-snapshots")
        }
        maven {
            url = uri("https://maven.juspay.in/jp-build-packages/hyper-sdk/")
        }
        maven {
            url = uri("https://maven.juspay.in/jp-build-packages/hypersdk-asset-download/releases/")
        }
        maven {
            url = uri("https://maven.getsimpl.com")
        }
    }
}

rootProject.name = "IxigoSDKSample"
include(":app")
 