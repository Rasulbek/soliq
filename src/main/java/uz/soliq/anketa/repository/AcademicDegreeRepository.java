package uz.soliq.anketa.repository;

import uz.soliq.anketa.domain.AcademicDegree;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AcademicDegree entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcademicDegreeRepository extends JpaRepository<AcademicDegree, Long> {
}
