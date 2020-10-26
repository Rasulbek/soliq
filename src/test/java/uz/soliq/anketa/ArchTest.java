package uz.soliq.anketa;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("uz.soliq.anketa");

        noClasses()
            .that()
                .resideInAnyPackage("uz.soliq.anketa.service..")
            .or()
                .resideInAnyPackage("uz.soliq.anketa.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..uz.soliq.anketa.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
