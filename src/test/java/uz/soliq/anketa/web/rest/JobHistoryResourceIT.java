package uz.soliq.anketa.web.rest;

import uz.soliq.anketa.SoliqApp;
import uz.soliq.anketa.domain.JobHistory;
import uz.soliq.anketa.repository.JobHistoryRepository;
import uz.soliq.anketa.service.JobHistoryService;
import uz.soliq.anketa.service.dto.JobHistoryDTO;
import uz.soliq.anketa.service.mapper.JobHistoryMapper;

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

/**
 * Integration tests for the {@link JobHistoryResource} REST controller.
 */
@SpringBootTest(classes = SoliqApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobHistoryResourceIT {

    private static final String DEFAULT_EMPLOYER = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYER = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_START_YEAR = 1950;
    private static final Integer UPDATED_START_YEAR = 1951;

    private static final Integer DEFAULT_END_YEAR = 1950;
    private static final Integer UPDATED_END_YEAR = 1951;

    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    @Autowired
    private JobHistoryMapper jobHistoryMapper;

    @Autowired
    private JobHistoryService jobHistoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobHistoryMockMvc;

    private JobHistory jobHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobHistory createEntity(EntityManager em) {
        JobHistory jobHistory = new JobHistory()
            .employer(DEFAULT_EMPLOYER)
            .jobTitle(DEFAULT_JOB_TITLE)
            .startYear(DEFAULT_START_YEAR)
            .endYear(DEFAULT_END_YEAR);
        return jobHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobHistory createUpdatedEntity(EntityManager em) {
        JobHistory jobHistory = new JobHistory()
            .employer(UPDATED_EMPLOYER)
            .jobTitle(UPDATED_JOB_TITLE)
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR);
        return jobHistory;
    }

    @BeforeEach
    public void initTest() {
        jobHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobHistory() throws Exception {
        int databaseSizeBeforeCreate = jobHistoryRepository.findAll().size();
        // Create the JobHistory
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);
        restJobHistoryMockMvc.perform(post("/api/job-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        JobHistory testJobHistory = jobHistoryList.get(jobHistoryList.size() - 1);
        assertThat(testJobHistory.getEmployer()).isEqualTo(DEFAULT_EMPLOYER);
        assertThat(testJobHistory.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testJobHistory.getStartYear()).isEqualTo(DEFAULT_START_YEAR);
        assertThat(testJobHistory.getEndYear()).isEqualTo(DEFAULT_END_YEAR);
    }

    @Test
    @Transactional
    public void createJobHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobHistoryRepository.findAll().size();

        // Create the JobHistory with an existing ID
        jobHistory.setId(1L);
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobHistoryMockMvc.perform(post("/api/job-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEmployerIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobHistoryRepository.findAll().size();
        // set the field null
        jobHistory.setEmployer(null);

        // Create the JobHistory, which fails.
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);


        restJobHistoryMockMvc.perform(post("/api/job-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJobTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobHistoryRepository.findAll().size();
        // set the field null
        jobHistory.setJobTitle(null);

        // Create the JobHistory, which fails.
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);


        restJobHistoryMockMvc.perform(post("/api/job-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobHistoryRepository.findAll().size();
        // set the field null
        jobHistory.setStartYear(null);

        // Create the JobHistory, which fails.
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);


        restJobHistoryMockMvc.perform(post("/api/job-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobHistories() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList
        restJobHistoryMockMvc.perform(get("/api/job-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].employer").value(hasItem(DEFAULT_EMPLOYER)))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].startYear").value(hasItem(DEFAULT_START_YEAR)))
            .andExpect(jsonPath("$.[*].endYear").value(hasItem(DEFAULT_END_YEAR)));
    }
    
    @Test
    @Transactional
    public void getJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        // Get the jobHistory
        restJobHistoryMockMvc.perform(get("/api/job-histories/{id}", jobHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobHistory.getId().intValue()))
            .andExpect(jsonPath("$.employer").value(DEFAULT_EMPLOYER))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE))
            .andExpect(jsonPath("$.startYear").value(DEFAULT_START_YEAR))
            .andExpect(jsonPath("$.endYear").value(DEFAULT_END_YEAR));
    }
    @Test
    @Transactional
    public void getNonExistingJobHistory() throws Exception {
        // Get the jobHistory
        restJobHistoryMockMvc.perform(get("/api/job-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        int databaseSizeBeforeUpdate = jobHistoryRepository.findAll().size();

        // Update the jobHistory
        JobHistory updatedJobHistory = jobHistoryRepository.findById(jobHistory.getId()).get();
        // Disconnect from session so that the updates on updatedJobHistory are not directly saved in db
        em.detach(updatedJobHistory);
        updatedJobHistory
            .employer(UPDATED_EMPLOYER)
            .jobTitle(UPDATED_JOB_TITLE)
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR);
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(updatedJobHistory);

        restJobHistoryMockMvc.perform(put("/api/job-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeUpdate);
        JobHistory testJobHistory = jobHistoryList.get(jobHistoryList.size() - 1);
        assertThat(testJobHistory.getEmployer()).isEqualTo(UPDATED_EMPLOYER);
        assertThat(testJobHistory.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testJobHistory.getStartYear()).isEqualTo(UPDATED_START_YEAR);
        assertThat(testJobHistory.getEndYear()).isEqualTo(UPDATED_END_YEAR);
    }

    @Test
    @Transactional
    public void updateNonExistingJobHistory() throws Exception {
        int databaseSizeBeforeUpdate = jobHistoryRepository.findAll().size();

        // Create the JobHistory
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobHistoryMockMvc.perform(put("/api/job-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobHistory in the database
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobHistory() throws Exception {
        // Initialize the database
        jobHistoryRepository.saveAndFlush(jobHistory);

        int databaseSizeBeforeDelete = jobHistoryRepository.findAll().size();

        // Delete the jobHistory
        restJobHistoryMockMvc.perform(delete("/api/job-histories/{id}", jobHistory.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobHistory> jobHistoryList = jobHistoryRepository.findAll();
        assertThat(jobHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
