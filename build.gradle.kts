import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion = "1.2.5"
val prometheusVersion = "0.7.0"
val logbackVersion = "1.2.3"
val logstashVersion = "5.1"
val konfigVersion = "1.6.10.0"
val kafkaVersion = "2.3.0"
val confluentVersion = "5.3.0"
val commonVersion = "9"
val gsonVersion = "2.8.6"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.50"
    application
}

val githubUser: String by project
val githubPassword: String by project

repositories {
    jcenter()
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/ktor")
    maven("http://packages.confluent.io/maven/")
    maven {
        url = uri("https://maven.pkg.github.com/navikt/btn-common")
        credentials {
            username = githubUser
            password = githubPassword
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")
    implementation("io.prometheus:simpleclient_hotspot:$prometheusVersion")
    implementation("io.prometheus:simpleclient_common:$prometheusVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashVersion")
    implementation("com.natpryce:konfig:$konfigVersion")
    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("io.confluent:kafka-json-serializer:$confluentVersion")
    implementation("no.nav.btn:btn-common:$commonVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")

    testCompile("org.jetbrains.kotlin:kotlin-test")
    testCompile("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClassName = "no.nav.btn.ApplicationKt"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Wrapper> {
    gradleVersion = "5.6.3"
}
