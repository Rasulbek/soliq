package uz.soliq.anketa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AcademicDegreeMapperTest {

    private AcademicDegreeMapper academicDegreeMapper;

    @BeforeEach
    public void setUp() {
        academicDegreeMapper = new AcademicDegreeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(academicDegreeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(academicDegreeMapper.fromId(null)).isNull();
    }
}
