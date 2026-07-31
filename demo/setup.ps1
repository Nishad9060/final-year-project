<#
    FootballIQ Backend API - Project Scaffolding Script
    ----------------------------------------------------
    Run this from PowerShell while your current directory
    is the empty project folder you want to set up:

        cd C:\path\to\your\empty\folder
        .\setup.ps1
#>

$ErrorActionPreference = "Stop"

function Write-ProjectFile {
    param(
        [Parameter(Mandatory = $true)][string]$RelativePath,
        [Parameter(Mandatory = $true)][string]$Content
    )
    $fullPath = Join-Path (Get-Location).Path $RelativePath
    [System.IO.File]::WriteAllText($fullPath, $Content)
    Write-Host "  Created file: $RelativePath" -ForegroundColor Green
}

Write-Host "FootballIQ Backend API - setting up project structure..." -ForegroundColor Cyan
Write-Host ""

# -----------------------------------------------------------------
# Directories
# -----------------------------------------------------------------
Write-Host "Creating directories..." -ForegroundColor Cyan

$directories = @(
    "src\main\java\com\footballiq\demo",
    "src\main\java\com\footballiq\demo\entity",
    "src\main\java\com\footballiq\demo\repository",
    "src\main\java\com\footballiq\demo\config",
    "src\main\java\com\footballiq\demo\controller",
    "src\main\resources"
)

foreach ($dir in $directories) {
    New-Item -ItemType Directory -Force -Path $dir | Out-Null
    Write-Host "  Created directory: $dir" -ForegroundColor Green
}

Write-Host ""
Write-Host "Writing project files..." -ForegroundColor Cyan

# -----------------------------------------------------------------
# pom.xml
# -----------------------------------------------------------------
$pomXml = @'
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.5</version>
        <relativePath/>
    </parent>

    <groupId>com.footballiq</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    <description>FootballIQ Backend API</description>

    <properties>
        <java.version>17</java.version>
        <jjwt.version>0.11.5</jjwt.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>${jjwt.version}</version>
        </dependency>

        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>${jjwt.version}</version>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>${jjwt.version}</version>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <finalName>demo</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
'@
Write-ProjectFile -RelativePath "pom.xml" -Content $pomXml

# -----------------------------------------------------------------
# application-prod.properties
# -----------------------------------------------------------------
$applicationProdProperties = @'
# =====================================================================
# FootballIQ Backend API - Production Profile
# Supabase PostgreSQL Configuration (HikariCP Connection Pool)
# =====================================================================

# ---------------------------------------------------------------------
# DataSource - replace "db.supabase.co" with your actual Supabase
# project host (e.g. db.abcdefghijklmnop.supabase.co), or set
# SPRING_DATASOURCE_URL to override this default entirely.
# ---------------------------------------------------------------------
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://db.supabase.co:5432/postgres?sslmode=require}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:postgres}
spring.datasource.password=${SUPABASE_DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

# ---------------------------------------------------------------------
# HikariCP Connection Pool
# ---------------------------------------------------------------------
spring.datasource.hikari.pool-name=FootballIQHikariPool
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.max-lifetime=1800000
spring.datasource.hikari.validation-timeout=5000
spring.datasource.hikari.connection-test-query=SELECT 1
spring.datasource.hikari.data-source-properties.sslmode=require

# ---------------------------------------------------------------------
# JPA / Hibernate
# ---------------------------------------------------------------------
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.open-in-view=false

# ---------------------------------------------------------------------
# Server
# ---------------------------------------------------------------------
server.port=${PORT:8080}
'@
Write-ProjectFile -RelativePath "src\main\resources\application-prod.properties" -Content $applicationProdProperties

# -----------------------------------------------------------------
# application.properties
# -----------------------------------------------------------------
$applicationProperties = @'
spring.application.name=demo
spring.profiles.active=prod
'@
Write-ProjectFile -RelativePath "src\main\resources\application.properties" -Content $applicationProperties

# -----------------------------------------------------------------
# entity/User.java
# -----------------------------------------------------------------
$userEntity = @'
package com.footballiq.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\entity\User.java" -Content $userEntity

# -----------------------------------------------------------------
# repository/UserRepository.java
# -----------------------------------------------------------------
$userRepository = @'
package com.footballiq.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.footballiq.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\repository\UserRepository.java" -Content $userRepository

# -----------------------------------------------------------------
# config/SecurityConfig.java
# -----------------------------------------------------------------
$securityConfig = @'
package com.footballiq.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\config\SecurityConfig.java" -Content $securityConfig

# -----------------------------------------------------------------
# controller/AuthController.java
# -----------------------------------------------------------------
$authController = @'
package com.footballiq.demo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("isAuthenticated", true);
        response.put("userId", 1);
        response.put("email", "demo@footballiq.com");

        return ResponseEntity.ok(response);
    }
}
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\controller\AuthController.java" -Content $authController

# -----------------------------------------------------------------
# DemoApplication.java
# -----------------------------------------------------------------
$demoApplication = @'
package com.footballiq.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
'@
Write-ProjectFile -RelativePath "src\main\java\com\footballiq\demo\DemoApplication.java" -Content $demoApplication

Write-Host ""
Write-Host "Project setup complete!" -ForegroundColor Green
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Cyan
Write-Host "  1. Set required environment variables, for example:"
Write-Host '       $env:SUPABASE_DB_PASSWORD = "your-actual-password"'
Write-Host "  2. Open application-prod.properties and replace db.supabase.co"
Write-Host "     with your actual Supabase project host."
Write-Host "  3. Build:  mvn clean install"
Write-Host "  4. Run:    mvn spring-boot:run"
Write-Host ""