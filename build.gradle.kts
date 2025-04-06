plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    compileOnly("org.projectlombok:lombok:1.18.24")
    runtimeOnly("org.postgresql:postgresql:42.6.0")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.8.2")
    implementation("org.telegram:telegrambots:6.9.7.1")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.4.4")
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
