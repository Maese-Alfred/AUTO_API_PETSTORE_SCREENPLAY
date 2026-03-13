plugins {
    id("java")
    id("net.serenity-bdd.serenity-gradle-plugin") version "4.2.34"
}

group = "org.auto"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

val serenityVersion = "4.2.34"
val junitVersion = "5.10.0"
val cucumberVersion = "7.15.0"
val hamcrestVersion = "2.2"

dependencies {
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-suite")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("net.serenity-bdd:serenity-core:$serenityVersion")
    testImplementation("net.serenity-bdd:serenity-junit5:$serenityVersion")
    testImplementation("net.serenity-bdd:serenity-cucumber:$serenityVersion")
    testImplementation("net.serenity-bdd:serenity-screenplay:$serenityVersion")
    testImplementation("net.serenity-bdd:serenity-screenplay-rest:$serenityVersion")
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:$cucumberVersion")
    testImplementation("org.hamcrest:hamcrest:$hamcrestVersion")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("cucumber.plugin", "pretty, io.cucumber.core.plugin.SerenityReporterParallel")
    systemProperty("cucumber.glue", "org.auto.screenplay.stepdefinitions")
    systemProperty("cucumber.features", "src/test/resources/features")
    finalizedBy("aggregate")
}
