package config

import org.gradle.api.Project
import java.io.ByteArrayOutputStream

fun Project.getCommits(): String {
    return try {
        val os = ByteArrayOutputStream()
        val command = "git rev-list --count --all"
            .split(" ")

        exec {
            commandLine = command
            standardOutput = os
        }

        String(os.toByteArray()).replace("\n", "")
    } catch (ex: Exception) {
        println(ex.message.orEmpty())
        ""
    }
}