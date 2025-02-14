import org.gradle.internal.impldep.bsh.commands.dir

plugins {
    id("java")
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("jacoco")
}

group = "br.com.ludevsp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.h2database:h2")
    testImplementation("io.rest-assured:spring-mock-mvc:3.0.0")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.mockito:mockito-core:2.1.0")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testCompileOnly ("org.projectlombok:lombok:1.18.34")
    compileOnly ("org.projectlombok:lombok:1.18.34")
    annotationProcessor ("org.projectlombok:lombok:1.18.34")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.34")
    testImplementation ("org.skyscreamer:jsonassert:1.5.0")


}

tasks.withType<JacocoReport> {
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it) {
                exclude("br/com/ludevsp/dataprovider/DataSeeder.class")
                exclude("br/com/ludevsp/Main.class")
                exclude("br/com/ludevsp/domain/dto")
            }
        }))
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}