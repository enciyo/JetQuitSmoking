plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
apply<plugins.AndroidLibraryProjectPlugin>()

dependencies{
    implementation(project(":shared"))
}