import com.vanniktech.maven.publish.SonatypeHost

plugins {
    java
    `version-catalog`
//    `maven-publish`
    signing
    id("com.vanniktech.maven.publish") version "0.30.0"
}

group = "io.github.alexritian"
version = "0.0.3-SNAPSHOT"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

catalog {
    versionCatalog {
        // Define versions
        version("publish", "0.30.0")
        version("commons-math3", "3.6.1")
        version("guava", "33.2.1-jre")
        version("junit-jupiter", "5.10.3")
        version("jooq", "3.19.1")
        version("flyway", "10.12.0")
        version("testcontainers", "1.19.8")
        version("spring-boot", "3.3.5")
        version("gradle-jooq-plugin", "9.0")
        version("postgresql", "42.7.3")
        version("lombok", "1.18.32")

        // Define plugins
        plugin("publish", "com.vanniktech.maven.publish").versionRef("publish")
        plugin("flyway", "org.flywaydb.flyway").versionRef("flyway")
        plugin("gradle-jooq-plugin", "nu.studer.jooq").versionRef("gradle-jooq-plugin")
        plugin("spring-boot", "org.springframework.boot").versionRef("spring-boot")

        // Define libraries
        library("commons-math3", "org.apache.commons", "commons-math3").versionRef("commons-math3")
        library("guava", "com.google.guava", "guava").versionRef("guava")
        library("junit-jupiter", "org.junit.jupiter", "junit-jupiter").versionRef("junit-jupiter")
        library("lombok", "org.projectlombok", "lombok").versionRef("lombok")

        // Spring Boot libraries
        library("spring-boot-starter", "org.springframework.boot", "spring-boot-starter").versionRef("spring-boot")
        library("spring-boot-starter-test", "org.springframework.boot", "spring-boot-starter-test").versionRef("spring-boot")
        library("spring-boot-starter-web", "org.springframework.boot", "spring-boot-starter-web").versionRef("spring-boot")
        library("spring-boot-starter-jooq", "org.springframework.boot", "spring-boot-starter-jooq").versionRef("spring-boot")
        library("spring-boot-devtools", "org.springframework.boot", "spring-boot-devtools").versionRef("spring-boot")
        library("spring-boot-testcontainers", "org.springframework.boot", "spring-boot-testcontainers").versionRef("spring-boot")

        // Flyway libraries
        library("flyway-core", "org.flywaydb", "flyway-core").versionRef("flyway")
        library("flyway-database-postgresql", "org.flywaydb", "flyway-database-postgresql").versionRef("flyway")

        // JOOQ libraries
        library("jooq", "org.jooq", "jooq").versionRef("jooq")
        library("jooq-codegen", "org.jooq", "jooq-codegen").versionRef("jooq")

        // Database library
        library("postgresql", "org.postgresql", "postgresql").versionRef("postgresql")

        // Testcontainers libraries
        library("testcontainers-postgresql", "org.testcontainers", "postgresql").versionRef("testcontainers")
        library("testcontainers-junit-jupiter", "org.testcontainers", "junit-jupiter").versionRef("testcontainers")

        // bundles
        bundle(
            "spring-boot-starter-all", listOf(
                "spring-boot-starter",
                "spring-boot-starter-test",
                "spring-boot-starter-web",
                "spring-boot-starter-jooq",
                "postgresql",
            )
        )
    }
}

publishing {
    publications {
        create<MavenPublication>("catalog") {
            from(components["versionCatalog"])
        }
    }

    repositories {
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/AlexRITIAN/catalog")
            credentials(PasswordCredentials::class)
        }
    }
}
mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(project.group.toString(), project.name, project.version.toString())
    pom {
        name.set("${project.group}:${project.name}")
        description.set("This project provides runtime support libraries required by Codegen-gradle-plugin")
        url.set("https://github.com/AlexRITIAN/codegn-gradle-plugin-runtime")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("AlexRITIAN")
                name.set("Too_Young")
                email.set("yhritianfq@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/AlexRITIAN/codegn-gradle-plugin-runtime")
            connection.set("scm:git:git://github.com/AlexRITIAN/codegn-gradle-plugin-runtime")
            developerConnection.set("scm:git:ssh://git@github.com:AlexRITIAN/codegn-gradle-plugin-runtime.git")
        }
    }
}