plugins{
    `kotlin-dsl`
}
repositories {
    google()
    mavenCentral()
}


dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.android.tools.build:gradle:7.2.1")
    implementation("com.android.tools.build:gradle-api:7.0.4")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.10")
    implementation("com.squareup:javapoet:1.13.0")
    implementation(gradleApi())
}