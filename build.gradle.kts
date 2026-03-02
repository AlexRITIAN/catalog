plugins {
    `version-catalog`
    signing
    id("com.vanniktech.maven.publish") version "0.36.0"
}

group = "io.github.alexritian"
version = "0.0.14"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

catalog {
    versionCatalog {
        // plugin versions
        version("publish", "0.36.0")
        version("flyway", "10.12.0")
        version("gradle-jooq-plugin", "9.0")
        version("plugin-publish", "1.3.0")

        // library versions
        version("junit-jupiter", "5.11.3")
        version("junit-platform-launcher", "1.11.3")
        version("testcontainers", "1.20.3")
        version("spring-boot", "3.5.0")
        version("jooq", "3.19.29")
        version("postgresql", "42.7.4")
        version("lombok", "1.18.34")
        version("annotations", "26.0.1")

        // plugins
        plugin("publish", "com.vanniktech.maven.publish").versionRef("publish")
        plugin("flyway", "org.flywaydb.flyway").versionRef("flyway")
        plugin("gradle-jooq-plugin", "nu.studer.jooq").versionRef("gradle-jooq-plugin")
        plugin("spring-boot", "org.springframework.boot").versionRef("spring-boot")
        plugin("plugin-publish", "com.gradle.plugin-publish").versionRef("plugin-publish")

        // libraries
        library("junit-jupiter", "org.junit.jupiter", "junit-jupiter").versionRef("junit-jupiter")
        library("junit-platform-launcher", "org.junit.platform", "junit-platform-launcher").versionRef("junit-platform-launcher")
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
        library("jooq-bom", "org.jooq", "jooq-bom").versionRef("jooq")
        library("gradle-jooq-plugin", "nu.studer", "gradle-jooq-plugin").versionRef("gradle-jooq-plugin")
        // Database library
        library("postgresql", "org.postgresql", "postgresql").versionRef("postgresql")

        // Testcontainers libraries
        library("testcontainers-postgresql", "org.testcontainers", "postgresql").versionRef("testcontainers")
        library("testcontainers-junit-jupiter", "org.testcontainers", "junit-jupiter").versionRef("testcontainers")

        library("jetbrains-annotations", "org.jetbrains", "annotations").versionRef("annotations")
        // bundles
        bundle(
            "spring-boot-starter-web-pg-jooq", listOf(
                "spring-boot-starter",
                "spring-boot-starter-test",
                "spring-boot-starter-web",
                "spring-boot-starter-jooq",
                "postgresql",
            )
        )
        bundle("jooq-all", listOf("jooq", "jooq-codegen"))
        bundle("testcontainers-pg", listOf("testcontainers-postgresql", "testcontainers-junit-jupiter"))
        bundle("flyway-pg", listOf("flyway-core", "flyway-database-postgresql"))
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(project.group.toString(), project.name, project.version.toString())
    pom {
        name.set("${project.group}:${project.name}")
        description.set("This project provides runtime support libraries required by Codegen-gradle-plugin")
        url.set("https://github.com/AlexRITIAN/catalog")

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
            url.set("https://github.com/AlexRITIAN/catalog")
            connection.set("scm:git:git://github.com/AlexRITIAN/catalog")
            developerConnection.set("scm:git:ssh://git@github.com:AlexRITIAN/catalog.git")
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}