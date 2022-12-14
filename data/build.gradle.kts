plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}
apply<plugins.AndroidLibraryProjectPlugin>()

dependencies {
    implementation(Lib.room)
    implementation(project(":shared"))
    implementation(project(":domain"))
    kapt(Lib.Kapt.room)
}