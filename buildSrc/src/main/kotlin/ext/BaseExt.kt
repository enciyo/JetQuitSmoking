import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.CompileOptions
import com.android.build.gradle.internal.dsl.DefaultConfig
import org.gradle.api.JavaVersion

fun BaseExtension.configureAndroid() = apply {
    configureAndroidLibrary().apply {
        defaultConfig.applicationId = AndroidSettings.applicationId
        composeOptions.kotlinCompilerExtensionVersion = LibVer.composeCompiler
        buildFeatures.compose = true
        packagingOptions.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

fun BaseExtension.configureAndroidLibrary() = apply {
    compileSdkVersion(AndroidSettings.compileSdkVersion)
    defaultConfig.configure()
    compileOptions.configure()
}

fun DefaultConfig.configure() = apply {
    minSdk = AndroidSettings.minSdk
    targetSdk = AndroidSettings.targetSdk
    versionCode = AppVersion.code
    versionName = AppVersion.name
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

fun CompileOptions.configure() = apply {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


