package com.attendance;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.attendance")
public class ArchitectureTest {

    @ArchTest
    static final ArchRule hexagonal_architecture_is_respected = layeredArchitecture()
            .consideringAllDependencies()
            // Definición de capas basada en tu estructura real
            .layer("Domain").definedBy("com.attendance.domain..")
            .layer("Application").definedBy("com.attendance.application..")
            .layer("Infrastructure").definedBy("com.attendance.infrastructure..")

            // Reglas de acceso (El flujo debe ser hacia el centro)
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Infrastructure")
            .whereLayer("Application").mayOnlyBeAccessedByLayers("Infrastructure")
            .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer();

    @ArchTest
    static final ArchRule domain_is_independent_of_frameworks = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAPackage("org.springframework..")
            .orShould().dependOnClassesThat().resideInAPackage("jakarta.persistence..")
            .as("El Dominio debe ser Java puro y no conocer nada de Spring o JPA");

    @ArchTest
    static final ArchRule application_does_not_depend_on_infrastructure = noClasses()
            .that().resideInAPackage("..application..")
            .should().dependOnClassesThat().resideInAPackage("..infrastructure..")
            .as("La capa de Aplicación no debe conocer detalles técnicos de la Infraestructura");
}
