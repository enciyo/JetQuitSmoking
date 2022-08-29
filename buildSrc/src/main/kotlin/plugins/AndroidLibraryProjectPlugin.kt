package plugins

import Lib
import PluginIds
import com.android.build.gradle.BaseExtension
import configureAndroidLibrary
import implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryProjectPlugin : Plugin<Project> {

    private val plugins = listOf(PluginIds.kapt)

    private val implementations = listOf(Lib.coreKtx, Lib.dateTime, Lib.moshi)

    override fun apply(target: Project) {
        plugins.forEach(target.plugins::apply)
        target.extensions.getByType<BaseExtension>().configureAndroidLibrary()
        target.plugins.apply(CommonProjectPlugin::class.java)
        target.dependencies.configureDependencies()
    }

    private fun DependencyHandler.configureDependencies() = apply {
        implementations.forEach(::implementation)
    }

}