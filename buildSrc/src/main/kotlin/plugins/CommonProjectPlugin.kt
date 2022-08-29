package plugins

import PluginIds
import org.gradle.api.Plugin
import org.gradle.api.Project
import Lib
import implementation
import kapt
import org.gradle.api.artifacts.dsl.DependencyHandler

class CommonProjectPlugin : Plugin<Project> {


    override fun apply(target: Project) {
        plugins.forEach(target.plugins::apply)
        target.dependencies.configureDependencies()
    }

    private val plugins = listOf(PluginIds.kapt, PluginIds.kotlinAndroid, PluginIds.hilt)
    private val implementations = listOf(Lib.hilt, Lib.javaPoet, Lib.coroutine)
    private val kapts = listOf(Lib.Kapt.hilt)

    private fun DependencyHandler.configureDependencies() = apply {
        implementations.forEach(::implementation)
        kapts.forEach(::kapt)
    }


}