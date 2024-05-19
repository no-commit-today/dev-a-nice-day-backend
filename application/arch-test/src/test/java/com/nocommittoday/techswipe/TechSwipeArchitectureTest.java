package com.nocommittoday.techswipe;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.nocommittoday.techswipe")
class TechSwipeArchitectureTest {

    @ArchTest
    ArchRule domain_패키지는_api_패키지를_참조하지_않음 = noClasses().that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAPackage("..api..")
            .allowEmptyShould(true);

}
