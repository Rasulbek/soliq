package uz.soliq.anketa.web.rest;

import uz.soliq.anketa.service.AcademicDegreeService;
import uz.soliq.anketa.web.rest.errors.BadRequestAlertException;
import uz.soliq.anketa.service.dto.AcademicDegreeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link uz.soliq.anketa.domain.AcademicDegree}.
 */
@RestController
@RequestMapping("/api")
public class AcademicDegreeResource {

    private final Logger log = LoggerFactory.getLogger(AcademicDegreeResource.class);

    private static final String ENTITY_NAME = "academicDegree";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcademicDegreeService academicDegreeService;

    public AcademicDegreeResource(AcademicDegreeService academicDegreeService) {
        this.academicDegreeService = academicDegreeService;
    }

    /**
     * {@code POST  /academic-degrees} : Create a new academicDegree.
     *
     * @param academicDegreeDTO the academicDegreeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new academicDegreeDTO, or with status {@code 400 (Bad Request)} if the academicDegree has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/academic-degrees")
    public ResponseEntity<AcademicDegreeDTO> createAcademicDegree(@Valid @RequestBody AcademicDegreeDTO academicDegreeDTO) throws URISyntaxException {
        log.debug("REST request to save AcademicDegree : {}", academicDegreeDTO);
        if (academicDegreeDTO.getId() != null) {
            throw new BadRequestAlertException("A new academicDegree cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademicDegreeDTO result = academicDegreeService.save(academicDegreeDTO);
        return ResponseEntity.created(new URI("/api/academic-degrees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /academic-degrees} : Updates an existing academicDegree.
     *
     * @param academicDegreeDTO the academicDegreeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicDegreeDTO,
     * or with status {@code 400 (Bad Request)} if the academicDegreeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the academicDegreeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/academic-degrees")
    public ResponseEntity<AcademicDegreeDTO> updateAcademicDegree(@Valid @RequestBody AcademicDegreeDTO academicDegreeDTO) throws URISyntaxException {
        log.debug("REST request to update AcademicDegree : {}", academicDegreeDTO);
        if (academicDegreeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AcademicDegreeDTO result = academicDegreeService.save(academicDegreeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, academicDegreeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /academic-degrees} : get all the academicDegrees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of academicDegrees in body.
     */
    @GetMapping("/academic-degrees")
    public ResponseEntity<List<AcademicDegreeDTO>> getAllAcademicDegrees(Pageable pageable) {
        log.debug("REST request to get a page of AcademicDegrees");
        Page<AcademicDegreeDTO> page = academicDegreeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /academic-degrees/:id} : get the "id" academicDegree.
     *
     * @param id the id of the academicDegreeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the academicDegreeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/academic-degrees/{id}")
    public ResponseEntity<AcademicDegreeDTO> getAcademicDegree(@PathVariable Long id) {
        log.debug("REST request to get AcademicDegree : {}", id);
        Optional<AcademicDegreeDTO> academicDegreeDTO = academicDegreeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(academicDegreeDTO);
    }

    /**
     * {@code DELETE  /academic-degrees/:id} : delete the "id" academicDegree.
     *
     * @param id the id of the academicDegreeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/academic-degrees/{id}")
    public ResponseEntity<Void> deleteAcademicDegree(@PathVariable Long id) {
        log.debug("REST request to delete AcademicDegree : {}", id);
        academicDegreeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
