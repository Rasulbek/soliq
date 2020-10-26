package uz.soliq.anketa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.soliq.anketa.web.rest.TestUtil;

public class AcademicDegreeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicDegreeDTO.class);
        AcademicDegreeDTO academicDegreeDTO1 = new AcademicDegreeDTO();
        academicDegreeDTO1.setId(1L);
        AcademicDegreeDTO academicDegreeDTO2 = new AcademicDegreeDTO();
        assertThat(academicDegreeDTO1).isNotEqualTo(academicDegreeDTO2);
        academicDegreeDTO2.setId(academicDegreeDTO1.getId());
        assertThat(academicDegreeDTO1).isEqualTo(academicDegreeDTO2);
        academicDegreeDTO2.setId(2L);
        assertThat(academicDegreeDTO1).isNotEqualTo(academicDegreeDTO2);
        academicDegreeDTO1.setId(null);
        assertThat(academicDegreeDTO1).isNotEqualTo(academicDegreeDTO2);
    }
}
