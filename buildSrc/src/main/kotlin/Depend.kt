object AndroidSettings {
    const val compileSdkVersion = 32
    const val applicationId = "com.enciyo.jetquitsmoking"
    const val minSdk = 21
    const val targetSdk = 32
}

object AppVersion {
    const val code = 1
    const val name = "1.0.0.$code"
}


object PluginIds {
    const val kapt: String = "kotlin-kapt"
    const val kotlinAndroid = "kotlin-android"
    const val hilt = "com.google.dagger.hilt.android"
}


object LibVer {
    const val coreKtx = "1.8.0"
    const val coroutine = "1.6.4"
    const val lifecycleAware = "2.6.0-alpha01"
    const val compose = "1.2.1"
    const val composeCompiler = "1.3.0"
    const val composeActivity = "1.3.1"
    const val material = "1.2.1"
    const val lottie = "5.2.0"
    const val navigation = "2.5.1"
    const val room = "2.4.3"
    const val hilt = "2.43.2"
    const val hiltNavigation = "1.0.0"
    const val dateTime = "0.4.0"
    const val moshi = "1.13.0"

    object Test {
        const val jUnit = "4.13.2"
    }

    object AndroidTest {
        const val jUnit = "1.1.3"
        const val espresso = "3.4.0"
    }
}

object Lib {
    const val coreKtx = "androidx.core:core-ktx:${LibVer.coreKtx}"
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${LibVer.coroutine}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibVer.lifecycleAware}"
    const val lifecycleScope = "androidx.lifecycle:lifecycle-runtime-ktx:${LibVer.lifecycleAware}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${LibVer.lifecycleAware}"
    const val compose = "androidx.compose.ui:ui:${LibVer.compose}"
    const val composePreview = "androidx.compose.ui:ui-tooling-preview:${LibVer.compose}"
    const val composeTooling = "androidx.compose.ui:ui-tooling:${LibVer.compose}"
    const val composeTestManifest = "androidx.compose.ui:ui-test-manifest:${LibVer.compose}"
    const val composeActivity = "androidx.activity:activity-compose:${LibVer.composeActivity}"
    const val material = "androidx.compose.material:material:${LibVer.material}"
    const val lottie = "com.airbnb.android:lottie-compose:${LibVer.lottie}"
    const val navigation = "androidx.navigation:navigation-compose:${LibVer.navigation}"
    const val room = "androidx.room:room-ktx:${LibVer.room}"
    const val hilt = "com.google.dagger:hilt-android:${LibVer.hilt}"
    const val javaPoet = "com.squareup:javapoet:1.13.0"
    const val viewModelForCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${LibVer.lifecycleAware}"
    const val lifecycleRuntimeCompose =
        "androidx.lifecycle:lifecycle-runtime-compose:${LibVer.lifecycleAware}"
    const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${LibVer.hiltNavigation}"
    const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${LibVer.dateTime}"
    const val moshi = "com.squareup.moshi:moshi-kotlin:${LibVer.moshi}"


    object Kapt {
        const val room = "androidx.room:room-compiler:${LibVer.room}"
        const val hilt = "com.google.dagger:hilt-compiler:${LibVer.hilt}"
        const val lifecycle = "androidx.lifecycle:lifecycle-compiler:${LibVer.lifecycleAware}"

    }

    object Test {
        const val jUnit = "junit:junit:${LibVer.Test.jUnit}"
    }

    object AndroidTest {
        const val jUnit = "androidx.test.ext:junit:${LibVer.AndroidTest.jUnit}"
        const val espresso =
            "androidx.test.espresso:espresso-core:${LibVer.AndroidTest.espresso}"
        const val compose = "androidx.compose.ui:ui-test-junit4:${LibVer.compose}"
    }

}
