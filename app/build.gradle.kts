plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.music_player_android_java"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.music_player_android_java"
        minSdk = 26
        targetSdk = 36

        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}