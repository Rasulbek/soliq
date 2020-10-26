package uz.soliq.anketa.web.rest;

import uz.soliq.anketa.SoliqApp;
import uz.soliq.anketa.domain.AcademicDegree;
import uz.soliq.anketa.repository.AcademicDegreeRepository;
import uz.soliq.anketa.service.AcademicDegreeService;
import uz.soliq.anketa.service.dto.AcademicDegreeDTO;
import uz.soliq.anketa.service.mapper.AcademicDegreeMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import uz.soliq.anketa.domain.enumeration.Degree;
/**
 * Integration tests for the {@link AcademicDegreeResource} REST controller.
 */
@SpringBootTest(classes = SoliqApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AcademicDegreeResourceIT {

    private static final String DEFAULT_INSTITUTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUTION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISCIPLINE = "AAAAAAAAAA";
    private static final String UPDATED_DISCIPLINE = "BBBBBBBBBB";

    private static final Integer DEFAULT_START_YEAR = 1950;
    private static final Integer UPDATED_START_YEAR = 1951;

    private static final Integer DEFAULT_END_YEAR = 1950;
    private static final Integer UPDATED_END_YEAR = 1951;

    private static final Degree DEFAULT_OBTAINED_DEGREE = Degree.ORTA;
    private static final Degree UPDATED_OBTAINED_DEGREE = Degree.ORTAMAXSUS;

    @Autowired
    private AcademicDegreeRepository academicDegreeRepository;

    @Autowired
    private AcademicDegreeMapper academicDegreeMapper;

    @Autowired
    private AcademicDegreeService academicDegreeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcademicDegreeMockMvc;

    private AcademicDegree academicDegree;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicDegree createEntity(EntityManager em) {
        AcademicDegree academicDegree = new AcademicDegree()
            .institutionName(DEFAULT_INSTITUTION_NAME)
            .discipline(DEFAULT_DISCIPLINE)
            .startYear(DEFAULT_START_YEAR)
            .endYear(DEFAULT_END_YEAR)
            .obtainedDegree(DEFAULT_OBTAINED_DEGREE);
        return academicDegree;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicDegree createUpdatedEntity(EntityManager em) {
        AcademicDegree academicDegree = new AcademicDegree()
            .institutionName(UPDATED_INSTITUTION_NAME)
            .discipline(UPDATED_DISCIPLINE)
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR)
            .obtainedDegree(UPDATED_OBTAINED_DEGREE);
        return academicDegree;
    }

    @BeforeEach
    public void initTest() {
        academicDegree = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcademicDegree() throws Exception {
        int databaseSizeBeforeCreate = academicDegreeRepository.findAll().size();
        // Create the AcademicDegree
        AcademicDegreeDTO academicDegreeDTO = academicDegreeMapper.toDto(academicDegree);
        restAcademicDegreeMockMvc.perform(post("/api/academic-degrees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicDegreeDTO)))
            .andExpect(status().isCreated());

        // Validate the AcademicDegree in the database
        List<AcademicDegree> academicDegreeList = academicDegreeRepository.findAll();
        assertThat(academicDegreeList).hasSize(databaseSizeBeforeCreate + 1);
        AcademicDegree testAcademicDegree = academicDegreeList.get(academicDegreeList.size() - 1);
        assertThat(testAcademicDegree.getInstitutionName()).isEqualTo(DEFAULT_INSTITUTION_NAME);
        assertThat(testAcademicDegree.getDiscipline()).isEqualTo(DEFAULT_DISCIPLINE);
        assertThat(testAcademicDegree.getStartYear()).isEqualTo(DEFAULT_START_YEAR);
        assertThat(testAcademicDegree.getEndYear()).isEqualTo(DEFAULT_END_YEAR);
        assertThat(testAcademicDegree.getObtainedDegree()).isEqualTo(DEFAULT_OBTAINED_DEGREE);
    }

    @Test
    @Transactional
    public void createAcademicDegreeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = academicDegreeRepository.findAll().size();

        // Create the AcademicDegree with an existing ID
        academicDegree.setId(1L);
        AcademicDegreeDTO academicDegreeDTO = academicDegreeMapper.toDto(academicDegree);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicDegreeMockMvc.perform(post("/api/academic-degrees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicDegreeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicDegree in the database
        List<AcademicDegree> academicDegreeList = academicDegreeRepository.findAll();
        assertThat(academicDegreeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInstitutionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicDegreeRepository.findAll().size();
        // set the field null
        academicDegree.setInstitutionName(null);

        // Create the AcademicDegree, which fails.
        AcademicDegreeDTO academicDegreeDTO = academicDegreeMapper.toDto(academicDegree);


        restAcademicDegreeMockMvc.perform(post("/api/academic-degrees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicDegreeDTO)))
            .andExpect(status().isBadRequest());

        List<AcademicDegree> academicDegreeList = academicDegreeRepository.findAll();
        assertThat(academicDegreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDisciplineIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicDegreeRepository.findAll().size();
        // set the field null
        academicDegree.setDiscipline(null);

        // Create the AcademicDegree, which fails.
        AcademicDegreeDTO academicDegreeDTO = academicDegreeMapper.toDto(academicDegree);


        restAcademicDegreeMockMvc.perform(post("/api/academic-degrees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicDegreeDTO)))
            .andExpect(status().isBadRequest());

        List<AcademicDegree> academicDegreeList = academicDegreeRepository.findAll();
        assertThat(academicDegreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicDegreeRepository.findAll().size();
        // set the field null
        academicDegree.setStartYear(null);

        // Create the AcademicDegree, which fails.
        AcademicDegreeDTO academicDegreeDTO = academicDegreeMapper.toDto(academicDegree);


        restAcademicDegreeMockMvc.perform(post("/api/academic-degrees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicDegreeDTO)))
            .andExpect(status().isBadRequest());

        List<AcademicDegree> academicDegreeList = academicDegreeRepository.findAll();
        assertThat(academicDegreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObtainedDegreeIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicDegreeRepository.findAll().size();
        // set the field null
        academicDegree.setObtainedDegree(null);

        // Create the AcademicDegree, which fails.
        AcademicDegreeDTO academicDegreeDTO = academicDegreeMapper.toDto(academicDegree);


        restAcademicDegreeMockMvc.perform(post("/api/academic-degrees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicDegreeDTO)))
            .andExpect(status().isBadRequest());

        List<AcademicDegree> academicDegreeList = academicDegreeRepository.findAll();
        assertThat(academicDegreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAcademicDegrees() throws Exception {
        // Initialize the database
        academicDegreeRepository.saveAndFlush(academicDegree);

        // Get all the academicDegreeList
        restAcademicDegreeMockMvc.perform(get("/api/academic-degrees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicDegree.getId().intValue())))
            .andExpect(jsonPath("$.[*].institutionName").value(hasItem(DEFAULT_INSTITUTION_NAME)))
            .andExpect(jsonPath("$.[*].discipline").value(hasItem(DEFAULT_DISCIPLINE)))
            .andExpect(jsonPath("$.[*].startYear").value(hasItem(DEFAULT_START_YEAR)))
            .andExpect(jsonPath("$.[*].endYear").value(hasItem(DEFAULT_END_YEAR)))
            .andExpect(jsonPath("$.[*].obtainedDegree").value(hasItem(DEFAULT_OBTAINED_DEGREE.toString())));
    }
    
    @Test
    @Transactional
    public void getAcademicDegree() throws Exception {
        // Initialize the database
        academicDegreeRepository.saveAndFlush(academicDegree);

        // Get the academicDegree
        restAcademicDegreeMockMvc.perform(get("/api/academic-degrees/{id}", academicDegree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(academicDegree.getId().intValue()))
            .andExpect(jsonPath("$.institutionName").value(DEFAULT_INSTITUTION_NAME))
            .andExpect(jsonPath("$.discipline").value(DEFAULT_DISCIPLINE))
            .andExpect(jsonPath("$.startYear").value(DEFAULT_START_YEAR))
            .andExpect(jsonPath("$.endYear").value(DEFAULT_END_YEAR))
            .andExpect(jsonPath("$.obtainedDegree").value(DEFAULT_OBTAINED_DEGREE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAcademicDegree() throws Exception {
        // Get the academicDegree
        restAcademicDegreeMockMvc.perform(get("/api/academic-degrees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcademicDegree() throws Exception {
        // Initialize the database
        academicDegreeRepository.saveAndFlush(academicDegree);

        int databaseSizeBeforeUpdate = academicDegreeRepository.findAll().size();

        // Update the academicDegree
        AcademicDegree updatedAcademicDegree = academicDegreeRepository.findById(academicDegree.getId()).get();
        // Disconnect from session so that the updates on updatedAcademicDegree are not directly saved in db
        em.detach(updatedAcademicDegree);
        updatedAcademicDegree
            .institutionName(UPDATED_INSTITUTION_NAME)
            .discipline(UPDATED_DISCIPLINE)
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR)
            .obtainedDegree(UPDATED_OBTAINED_DEGREE);
        AcademicDegreeDTO academicDegreeDTO = academicDegreeMapper.toDto(updatedAcademicDegree);

        restAcademicDegreeMockMvc.perform(put("/api/academic-degrees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicDegreeDTO)))
            .andExpect(status().isOk());

        // Validate the AcademicDegree in the database
        List<AcademicDegree> academicDegreeList = academicDegreeRepository.findAll();
        assertThat(academicDegreeList).hasSize(databaseSizeBeforeUpdate);
        AcademicDegree testAcademicDegree = academicDegreeList.get(academicDegreeList.size() - 1);
        assertThat(testAcademicDegree.getInstitutionName()).isEqualTo(UPDATED_INSTITUTION_NAME);
        assertThat(testAcademicDegree.getDiscipline()).isEqualTo(UPDATED_DISCIPLINE);
        assertThat(testAcademicDegree.getStartYear()).isEqualTo(UPDATED_START_YEAR);
        assertThat(testAcademicDegree.getEndYear()).isEqualTo(UPDATED_END_YEAR);
        assertThat(testAcademicDegree.getObtainedDegree()).isEqualTo(UPDATED_OBTAINED_DEGREE);
    }

    @Test
    @Transactional
    public void updateNonExistingAcademicDegree() throws Exception {
        int databaseSizeBeforeUpdate = academicDegreeRepository.findAll().size();

        // Create the AcademicDegree
        AcademicDegreeDTO academicDegreeDTO = academicDegreeMapper.toDto(academicDegree);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicDegreeMockMvc.perform(put("/api/academic-degrees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicDegreeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicDegree in the database
        List<AcademicDegree> academicDegreeList = academicDegreeRepository.findAll();
        assertThat(academicDegreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAcademicDegree() throws Exception {
        // Initialize the database
        academicDegreeRepository.saveAndFlush(academicDegree);

        int databaseSizeBeforeDelete = academicDegreeRepository.findAll().size();

        // Delete the academicDegree
        restAcademicDegreeMockMvc.perform(delete("/api/academic-degrees/{id}", academicDegree.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AcademicDegree> academicDegreeList = academicDegreeRepository.findAll();
        assertThat(academicDegreeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
