import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    `maven-publish`
}

val libraryVersion = "0.2.7"

android {
    namespace = "br.com.arml.composecollections"
    compileSdk = 37

    defaultConfig {
        applicationId = "br.com.arml.composecollections"
        minSdk = 24
        targetSdk = 37
        versionCode = 10
        versionName = libraryVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    publishing {
        singleVariant("release") {
            publishApk()
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                    project.layout.buildDirectory.dir("compose_metrics").get().asFile.absolutePath,
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                    project.layout.buildDirectory.dir("compose_metrics").get().asFile.absolutePath
        )
    }
}

dependencies {
    val composeBom = platform(libs.androidx.compose.bom)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(composeBom)

    implementation(libs.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.ui.tooling.preview)

    testImplementation(libs.junit)

    androidTestImplementation(composeBom)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit)

    debugImplementation(composeBom)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.ui.tooling)
}

group = "br.com.arml.composecollections"
version = libraryVersion

publishing {
    publications {
        register<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }

            groupId = "br.com.arml.composecollections"
            artifactId = "composecollections"
            version = libraryVersion

            pom {
                name.set("ComposeCollections")
                description.set("Componentes Compose para exibição de grandes coleções de dados com navegação rápida.")
                url.set("https://github.com/albertrml/ComposeCollections")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("albertrml")
                        name.set("Albert Richard")
                    }
                }
                scm {
                    connection.set("scm:git:github.com/albertrml/ComposeCollections.git")
                    developerConnection.set("scm:git:ssh://github.com/albertrml/ComposeCollections.git")
                    url.set("https://github.com/albertrml/ComposeCollections/tree/main")
                }
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/albertrml/ComposeCollections")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
