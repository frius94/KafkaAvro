import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.0"
	id("io.spring.dependency-management") version "1.1.0"
	id("com.github.davidmc24.gradle.plugin.avro") version "1.5.0"
	kotlin("jvm") version "1.7.21"
	kotlin("plugin.spring") version "1.7.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
	maven(url = "https://packages.confluent.io/maven/")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.apache.kafka:kafka-streams")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.apache.avro:avro:1.11.1")
	implementation("org.apache.kafka:kafka-clients:3.3.1")
	implementation("io.confluent:kafka-avro-serializer:7.3.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets["main"].java.srcDir(file("build/generated-main-avro-java"))