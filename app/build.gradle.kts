plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    id("vkid.manifest.placeholders")
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)

}

android {
    namespace = "ru.nvgsoft.vknewsclient"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.nvgsoft.vknewsclient"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


        addManifestPlaceholders(
            mapOf(
                "VKIDClientID" to "53466689", // ID вашего приложения (app_id).
                "VKIDClientSecret" to "ZFmkjHoS8S80VuhGv3Qa", // Ваш защищенный ключ (client_secret).
                "VKIDRedirectHost" to "vk.com", // Обычно используется vk.com.
                "VKIDRedirectScheme" to "vk53466689", // Обычно используется vk{ID приложения}.
            )
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.navigation.compose)

    //Gson
    implementation(libs.gson)

    //Coil
    implementation(libs.coil)
    //Coil compose
    implementation(libs.coil.compose)
    //Coil сеть
    implementation(libs.coil.network)

    //vk
    implementation(libs.vk.core)
    implementation(libs.vk.api)

    val sdkVersion = "2.3.1"
    implementation("com.vk.id:vkid:${sdkVersion}")
    implementation("com.vk.id:onetap-compose:${sdkVersion}")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")

    //OkHttpClient
    implementation(libs.okHttpClient)
    //HttpLoggingInterceptor
    implementation(libs.httpLoggingInterceptor)

    //Retrofit
    implementation(libs.retrofit)

    //Dagger2
    implementation(libs.dagger2)
//Dagger2 кодогенератор
    ksp(libs.dagger2.compiler)
//Dagger2 аннотации
    ksp(libs.dagger2.android.processor)


}