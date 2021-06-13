plugins {
    `kotlin-dsl`
}
repositories {
    mavenCentral()
    google()
}

dependencies {
    compileOnly(gradleKotlinDsl())
}