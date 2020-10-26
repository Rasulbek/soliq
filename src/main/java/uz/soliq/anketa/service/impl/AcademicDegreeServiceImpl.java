package uz.soliq.anketa.service.impl;

import uz.soliq.anketa.service.AcademicDegreeService;
import uz.soliq.anketa.domain.AcademicDegree;
import uz.soliq.anketa.repository.AcademicDegreeRepository;
import uz.soliq.anketa.service.dto.AcademicDegreeDTO;
import uz.soliq.anketa.service.mapper.AcademicDegreeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AcademicDegree}.
 */
@Service
@Transactional
public class AcademicDegreeServiceImpl implements AcademicDegreeService {

    private final Logger log = LoggerFactory.getLogger(AcademicDegreeServiceImpl.class);

    private final AcademicDegreeRepository academicDegreeRepository;

    private final AcademicDegreeMapper academicDegreeMapper;

    public AcademicDegreeServiceImpl(AcademicDegreeRepository academicDegreeRepository, AcademicDegreeMapper academicDegreeMapper) {
        this.academicDegreeRepository = academicDegreeRepository;
        this.academicDegreeMapper = academicDegreeMapper;
    }

    @Override
    public AcademicDegreeDTO save(AcademicDegreeDTO academicDegreeDTO) {
        log.debug("Request to save AcademicDegree : {}", academicDegreeDTO);
        AcademicDegree academicDegree = academicDegreeMapper.toEntity(academicDegreeDTO);
        academicDegree = academicDegreeRepository.save(academicDegree);
        return academicDegreeMapper.toDto(academicDegree);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AcademicDegreeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AcademicDegrees");
        return academicDegreeRepository.findAll(pageable)
            .map(academicDegreeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AcademicDegreeDTO> findOne(Long id) {
        log.debug("Request to get AcademicDegree : {}", id);
        return academicDegreeRepository.findById(id)
            .map(academicDegreeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AcademicDegree : {}", id);
        academicDegreeRepository.deleteById(id);
    }
}
