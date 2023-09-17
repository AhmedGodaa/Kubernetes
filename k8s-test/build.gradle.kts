import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

group = "com.example"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

// Adds Spring Boot Actuator, which provides production-ready features to help monitor and manage your application.
    implementation("org.springframework.boot:spring-boot-starter-actuator")

// Integrates Micrometer with Prometheus, allowing you to collect application metrics and expose them in a format that Prometheus can scrape.
    implementation("io.micrometer:micrometer-registry-prometheus")

// Provides a bridge between Micrometer and Brave, enabling tracing and instrumentation of your application using distributed tracing.
    implementation("io.micrometer:micrometer-tracing-bridge-brave")

// Integrates Brave with Zipkin, allowing you to report tracing data to a Zipkin server for distributed tracing and analysis.
    implementation("io.zipkin.reporter2:zipkin-reporter-brave")

// Adds support for monitoring and collecting metrics related to database calls using Micrometer. Helpful for understanding database performance.
    implementation("net.ttddyy.observation:datasource-micrometer-spring-boot:1.0.1")

// Adds support for Aspect-Oriented Programming (AOP) in Spring Boot, which can be used for intercepting method calls and applying cross-cutting concerns.
    implementation("org.springframework.boot:spring-boot-starter-aop")

// Integrates Logback with Loki, a log aggregation system. This allows you to send log entries to Loki for storage and analysis.
    implementation("com.github.loki4j:loki-logback-appender:1.4.1")


}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
