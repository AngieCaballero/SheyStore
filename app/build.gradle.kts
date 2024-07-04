plugins {
    id("com.google.devtools.ksp")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.angiedev.sheystore"
    compileSdk = 34
    android.buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "com.angiedev.sheystore"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    sourceSets.getByName("main").resources.srcDirs(
        "src/main/res/modules/login",
        "src/main/res/modules/main"
    )

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding {
            enable = true
        }
    }

    secrets {
        // Optionally specify a different file name containing your secrets.
        // The plugin defaults to "local.properties"
        propertiesFileName = "secrets.properties"

        // A properties file containing default secret values. This file can be
        // checked in version control.
        defaultPropertiesFileName = "local.defaults.properties"

        // Configure which keys should be ignored by the plugin by providing regular expressions.
        // "sdk.dir" is ignored by default.
        ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
        ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")

    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    implementation("androidx.fragment:fragment-ktx:1.8.1")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.7")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    ksp("com.google.dagger:hilt-android-compiler:2.51")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("com.google.firebase:firebase-storage")

    implementation("com.airbnb.android:lottie:6.4.0")

    implementation("com.tbuonomo:dotsindicator:5.0")

    implementation("androidx.datastore:datastore-preferences:1.1.1")

    implementation("com.github.98kmc:network-caller:1.0.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    implementation("com.patrykandpatrick.vico:compose-m3:1.15.0")
    implementation("com.patrykandpatrick.vico:core:1.15.0")
    implementation("com.patrykandpatrick.vico:views:1.15.0")

    implementation("com.squareup.moshi:moshi:1.15.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    implementation("com.google.maps.android:maps-ktx:5.0.0")
    implementation("com.google.android.gms:play-services-maps:19.0.0")

    implementation("io.github.chaosleung:pinview:1.4.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}