package com.nocommittoday;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.nocommittoday")
class ArchitectureTest {

    @ArchTest
    ArchRule module패키지는_techswipe패키지를_참조하지_않음 = noClasses().that().resideInAPackage("..module..")
            .should().dependOnClassesThat().resideInAPackage("..techswipe..")
            .allowEmptyShould(true);

    @ArchTest
    ArchRule module패키지는_client패키지를_참조하지_않음 = noClasses().that().resideInAPackage("..module..")
            .should().dependOnClassesThat().resideInAPackage("..client..")
            .allowEmptyShould(true);

    @ArchTest
    ArchRule client패키지는_techswipe패키지를_참조하지_않음 = noClasses().that().resideInAPackage("..client..")
            .should().dependOnClassesThat().resideInAPackage("..techswipe..")
            .allowEmptyShould(true);

}
