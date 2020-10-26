package uz.soliq.anketa.service;

import uz.soliq.anketa.service.dto.AcademicDegreeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link uz.soliq.anketa.domain.AcademicDegree}.
 */
public interface AcademicDegreeService {

    /**
     * Save a academicDegree.
     *
     * @param academicDegreeDTO the entity to save.
     * @return the persisted entity.
     */
    AcademicDegreeDTO save(AcademicDegreeDTO academicDegreeDTO);

    /**
     * Get all the academicDegrees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AcademicDegreeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" academicDegree.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AcademicDegreeDTO> findOne(Long id);

    /**
     * Delete the "id" academicDegree.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
