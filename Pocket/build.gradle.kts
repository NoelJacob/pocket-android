import tasks.registerCopyMergedManifestTask
import utils.*
import utils.pocket.*

plugins {
    pocketAndroidApp()
    kotlinKapt()
    kotlinCompose()
    hilt()
    safeArgsKotlin()
    kotlinSerialization()
    licensee()
    aboutLibraries()
}

val versionMajor = 8 // Max value of 200
val versionMinor = 33 // Max of two digits
val versionPatch = 0 // Max of two digits
val versionBuild = 0 // Max of three digits

// See usage and more details below, but this produces version numbers like 6.2.4.1 and codes like 60204001
// Also see Versioning section in README_WORKFLOW.md.

// Sensitive strings that should only be put into specific builds:
val serverDevSuffix = ".readitlater.com"

android {
    namespace = "com.ideashower.readitlater"
    defaultConfig {
        applicationId = "com.ideashower.readitlater.pro"

        buildStringField("GIT_SHA", getGitSha())
        buildStringField("API_KEY_PHONE", getSecret("API_KEY_PHONE"))
        buildStringField("API_KEY_TABLET", getSecret("API_KEY_TABLET"))
        buildStringField("API_DEV_SUFFIX", "")
        buildStringField("UA_PM", "Free")

        buildBooleanField("I_B", false) // I_B means isTeamBeta?



        // MMMmmppbbb
        versionCode = versionMajor * 10000000 + versionMinor * 100000 + versionPatch * 1000 + versionBuild
        // MMM.mm.pp.bbb
        versionName = "$versionMajor.$versionMinor.$versionPatch.$versionBuild"

        vectorDrawables.useSupportLibrary = true // https://medium.com/@chrisbanes/appcompat-v23-2-age-of-the-vectors-91cbafa87c88#.m9i38hx27
        resourceConfigurations.addAll(
            arrayOf(
                "de",
                "es",
                "es-rES",
                "fr",
                "fr-rCA",
                "it",
                "ja",
                "ko",
                "nl",
                "pl",
                "pt",
                "pt-rBR",
                "ru",
                "zh",
                "zh-rCN",
                "zh-rTW"
            )
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions.add(FlavorDimensions.TARGET)
    productFlavors {
        // Single FOSS flavor (F-Droid distribution; F-Droid signs the APK itself).
        register(Flavors.FDROID) {
            dimension = FlavorDimensions.TARGET

            buildStringField("MARKET_KEY", "fdroid")
            buildStringField("API_DEV_SUFFIX", serverDevSuffix)

            buildBooleanField("I_B", false)
        }
    }

    buildTypes {
        getByName(BuildTypes.DEBUG) {
            isMinifyEnabled = false
            isDebuggable = true
            matchingFallbacks.add("release")
        }

        register(BuildTypes.UNSIGNED_RELEASE) {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.txt")
            signingConfig = null
            matchingFallbacks.add("release")
        }
    }

    setupVariantFilters()


    packaging {
        resources { 
            merges.add("META-INF/LICENSE.txt")
            merges.add("META-INF/LICENSE.txt")
            merges.add("META-INF/LICENSE")
            merges.add("META-INF/NOTICE.txt")
            merges.add("META-INF/NOTICE")
            merges.add("META-INF/ASL2.0")
            excludes.add("build-data.properties") // Unless we have this, the tink library causes build issues
        }
    }

    lint {
        checkReleaseBuilds = false
        checkDependencies = true
    }
    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
    }
    dataBinding {
        enable = true
    }
}

licensee {
    allow("Apache-2.0")
    allow("MIT")
    allowUrl("https://jsoup.org/license") { because("self-hosted MIT") }
    allow("BSD-2-Clause")
    allowUrl("http://opensource.org/licenses/BSD-2-Clause")
    allowUrl("https://github.com/facebook/shimmer-android/blob/master/LICENSE") { because("self-hosted BSD") }
    allowUrl("https://raw.githubusercontent.com/ThreeTen/threetenbp/master/LICENSE.txt") { because("self-hosted BSD") }
    allow("MPL-1.1")
    allow("CC0-1.0")
    allowUrl("https://developer.android.com/studio/terms.html") { because("Android SDK") }
    allowUrl("https://developer.android.com/guide/playcore/license") { because("Play Core SDK ToS") }
}

dependencies {
    implementation(projects.syncPocketAndroid)
    // ponytail: analytics removed
    implementation(projects.pocketUi)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.viewbinding)
    implementation(libs.accompanist.drawablepainter)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.adaptive)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.fragment.compose)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.browser)
    implementation(libs.androidx.media)
    implementation(libs.androidx.work)

    implementation(Deps.AndroidX.SwipeRefreshLayout.swipeRefresh)
    implementation(Deps.AndroidX.Lifecycle.viewmodel)
    implementation(Deps.AndroidX.Lifecycle.viewmodelKtx)
    implementation(Deps.AndroidX.Lifecycle.viewmodelCompose)

    implementation(libs.kotlinx.serialization.json)



    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)

    implementation(libs.okhttp)
    implementation(libs.okhttp.logginginterceptor)

    implementation(Deps.Nikartm.imageSupport)

    implementation(libs.markwon)
    implementation(Deps.Jooq.joor)
    
    implementation(Deps.RxJava.RxAndroid.rxAndroid)
    implementation(Deps.Commons.IO.commonsIo)
    implementation(Deps.Apache.Mime4j.core)
    implementation(Deps.JSoup.jsoup)
    implementation(Deps.Google.JUniversalCharDet.juniversalchardet)

    implementation(Deps.JakeWharton.ThreeTenAbp.threeTen)

    implementation(libs.aboutlibraries)


    debugImplementation(libs.leakcanary)


    testImplementation(Deps.Mockito.core)
    testImplementation(Deps.AssertJ.core)
    testImplementation(libs.kotlin.junit)
    testImplementation(Deps.MockK.mockk)
    testImplementation(platform(libs.kotlinx.coroutines.bom))
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.turbine)

    androidTestImplementation(Deps.AndroidX.Test.rules)
    androidTestImplementation(libs.kotlin.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(Deps.Google.Play.core)

}

kapt {
    arguments {
        arg("dagger.fastInit", "enabled")
        arg("dagger.useBindingGraphFix", "ENABLED")
        arg("dagger.ignoreProvisionKeyWildcards", "ENABLED")
    }
}

registerCopyMergedManifestTask()
