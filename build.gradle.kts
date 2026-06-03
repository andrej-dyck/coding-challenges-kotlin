plugins {
    kotlin("jvm") version "2.4.0"
    id("dev.detekt") version "2.0.0-alpha.3"
}

group = "ad.kata"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // JUnit 5
    testImplementation(platform("org.junit:junit-bom:6.1.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // AssertJ
    testImplementation("org.assertj:assertj-core:3.27.7")
    // jqwik
    testImplementation("net.jqwik:jqwik:1.10.1")
}

/* Source sets by Kotlin conventions /src and /test */
sourceSets.main { kotlin.srcDirs("src/") }
sourceSets.test { kotlin.srcDirs("test/") }

/* Resources */
sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("test-resources")

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

/* Detekt */
detekt {
    source.setFrom("src/", "test/")
    config.setFrom("detekt.yml")
}

/* Check with Junit 5 only */
tasks.test {
    useJUnitPlatform {
        includeEngines("junit-jupiter", "jqwik")
        excludeEngines("junit-vintage")
    }
}

/* Gradle Wrapper */
tasks.withType<Wrapper> {
    gradleVersion = "9.5.1"
    distributionType = Wrapper.DistributionType.BIN
}
