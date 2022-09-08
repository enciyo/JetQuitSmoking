plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}
apply<plugins.AndroidProjectPlugin>()
dependencies{
    implementation(project(":data"))
    implementation(kotlin("reflect"))
}
