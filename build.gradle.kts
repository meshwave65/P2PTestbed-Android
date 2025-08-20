// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Usamos os aliases simplificados do libs.versions.toml
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
}
