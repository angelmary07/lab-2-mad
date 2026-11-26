pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LAB_MAD"

include(":WellbeingApp")
project(":WellbeingApp").projectDir = file("WellbeingApp/app")

include(":Lab_1A_Layouts")
project(":Lab_1A_Layouts").projectDir = file("Lab_1A_Layouts/app")

include(":Lab_1B_Views")
project(":Lab_1B_Views").projectDir = file("Lab_1B_Views/app")

include(":Lab_2_UserInteraction")
project(":Lab_2_UserInteraction").projectDir = file("Lab_2_UserInteraction/app")
