package uz.soliq.anketa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import uz.soliq.anketa.web.rest.TestUtil;

public class AcademicDegreeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicDegree.class);
        AcademicDegree academicDegree1 = new AcademicDegree();
        academicDegree1.setId(1L);
        AcademicDegree academicDegree2 = new AcademicDegree();
        academicDegree2.setId(academicDegree1.getId());
        assertThat(academicDegree1).isEqualTo(academicDegree2);
        academicDegree2.setId(2L);
        assertThat(academicDegree1).isNotEqualTo(academicDegree2);
        academicDegree1.setId(null);
        assertThat(academicDegree1).isNotEqualTo(academicDegree2);
    }
}
