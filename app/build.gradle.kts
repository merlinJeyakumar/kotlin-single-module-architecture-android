plugins {
    id("com.android.application")
    id("com.google.devtools.ksp")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = Configs.applicationId
    compileSdk = Configs.compileSdkVersion

    lint {
        abortOnError = false
        ignoreWarnings = true
    }

    defaultConfig {
        minSdk = 21
        targetSdk = Configs.targetSdkVersion
        applicationId = Configs.applicationId
        versionCode = Configs.versionCode
        versionName = Configs.versionName
        setMinSdkVersion(Configs.minSdkVersion)
        setTargetSdkVersion(Configs.targetSdkVersion.toString())
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
        testInstrumentationRunner = "com.nativedevps.expenses.CustomTestRunner"
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
    /*kotlinOptions {
        jvmTarget = "17"
    }*/

    lint {
        baseline = file("lint-baseline.xml")
    }

    packagingOptions {
        exclude("META-INF/LICENSE")
        exclude("META-INF/NOTICE")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/main.kotlin_module")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/INDEX.LIST")
        exclude("META-INF/library_release.kotlin_module")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        resources.excludes.add("META-INF/*")
    }
    flavorDimensions("development")
    buildTypes {
        getByName("release") {
            buildConfigField("Boolean", "Config", true.toString())
            isShrinkResources = false
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            buildConfigField("Boolean", "Config", false.toString())
            isDebuggable = true
        }
    }

    productFlavors {
        create("production") {
            applicationId = Configs.applicationId
        }
        create("development") {
            applicationId = "${Configs.applicationId}.development"
        }
    }
    configurations {
        all {
            exclude("commons-logging","commons-logging")
            exclude("org.apache.httpcomponents")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "../libs", "include" to listOf("*.aar", "*.jar"))))

    implementation(project(":jeyksupport"))
    implementation(project(NativeDevps.nativedevps))
    implementation(RequiredLibraries.core_ktx)
    implementation(RequiredLibraries.viewmodel_ktx)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    requiredLibraries()
    networkLibraries()
    roomLibraries()
    supportLibraries()
    imageLoaderLibraries()
    dataStoreLite()
    implementation(platform(FirebaseLibraries.firebase_platform_bom))
    firebaseLibraries()
    implementation(NavigationLibraries.navigationUi)
    implementation(NavigationLibraries.navigationFragment)
    testLibraries()
    testImplementation(kotlin("test"))
}

kapt {
    correctErrorTypes = true
}
