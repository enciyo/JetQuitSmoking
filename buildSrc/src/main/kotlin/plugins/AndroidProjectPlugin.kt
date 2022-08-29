package plugins

import Lib
import PluginIds
import androidTestImplementation
import com.android.build.gradle.BaseExtension
import configureAndroid
import debugImplementation
import implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.getByType
import testImplementation
import kapt

class AndroidProjectPlugin : Plugin<Project> {

    private val plugins = listOf(PluginIds.kotlinAndroid)

    private val implementations = listOf(
        Lib.coreKtx,
        Lib.compose,
        Lib.material,
        Lib.composePreview,
        Lib.composeActivity,
        Lib.viewModelForCompose,
        Lib.lifecycleRuntimeCompose,
        Lib.hiltNavigation,
        Lib.lifecycleScope,
        Lib.liveData,
        Lib.viewModel,
        Lib.lottie,
        Lib.navigation,
        Lib.dateTime
    )

    private val debugImplementations = listOf(
        Lib.composeTooling,
        Lib.composeTestManifest
    )

    private val testImplementations = listOf(
        Lib.Test.jUnit
    )

    private val androidTestImplementations = listOf(
        Lib.AndroidTest.espresso,
        Lib.AndroidTest.jUnit
    )

    private val kapts = listOf(
        Lib.Kapt.lifecycle
    )

    override fun apply(project: Project) {
        plugins.forEach(project.plugins::apply)
        project.extensions.getByType<BaseExtension>().configureAndroid()
        project.plugins.apply(CommonProjectPlugin::class.java)
        project.dependencies.configureDependencies()
    }

    private fun DependencyHandler.configureDependencies() = apply {
        implementations.forEach(::implementation)
        debugImplementations.forEach(::debugImplementation)
        testImplementations.forEach(::testImplementation)
        androidTestImplementations.forEach(::androidTestImplementation)
        kapts.forEach(::kapt)
    }

}