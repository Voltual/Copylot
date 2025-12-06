// settings.gradle.kts

pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
    // 添加阿里云镜像
    maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
  
  repositories {
    // 阿里云镜像 (必须添加)
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    maven { url = uri("https://maven.aliyun.com/repository/google") }
    
    // 标准仓库
    google()
    mavenCentral()
    
    // PhotoView 所需仓库
    maven { url = uri("https://jitpack.io") }
    
    // **NEW & FINAL FIX**: 将本地文件夹声明为一个 Maven 仓库
    maven { url = uri("$rootDir/ijkplayer-main") }
  }
}

rootProject.name = "Copylot"
include(":app")