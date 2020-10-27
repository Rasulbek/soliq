package uz.soliq.anketa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import uz.soliq.anketa.domain.enumeration.Degree;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A AcademicDegree.
 */
@Entity
@Table(name = "academic_degree")
public class AcademicDegree implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "institution_name", length = 250, nullable = false)
    private String institutionName;

    @NotNull
    @Size(max = 250)
    @Column(name = "discipline", length = 250, nullable = false)
    private String discipline;

    @NotNull
    @Min(value = 1950)
    @Max(value = 2050)
    @Column(name = "start_year", nullable = false)
    private Integer startYear;

    @Min(value = 1950)
    @Max(value = 2050)
    @Column(name = "end_year")
    private Integer endYear;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "obtained_degree", nullable = false)
    private Degree obtainedDegree;

    @ManyToOne
    @JsonIgnoreProperties(value = "universities", allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public AcademicDegree institutionName(String institutionName) {
        this.institutionName = institutionName;
        return this;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getDiscipline() {
        return discipline;
    }

    public AcademicDegree discipline(String discipline) {
        this.discipline = discipline;
        return this;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public AcademicDegree startYear(Integer startYear) {
        this.startYear = startYear;
        return this;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public AcademicDegree endYear(Integer endYear) {
        this.endYear = endYear;
        return this;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Degree getObtainedDegree() {
        return obtainedDegree;
    }

    public AcademicDegree obtainedDegree(Degree obtainedDegree) {
        this.obtainedDegree = obtainedDegree;
        return this;
    }

    public void setObtainedDegree(Degree obtainedDegree) {
        this.obtainedDegree = obtainedDegree;
    }

    public Employee getEmployee() {
        return employee;
    }

    public AcademicDegree employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcademicDegree)) {
            return false;
        }
        return id != null && id.equals(((AcademicDegree) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcademicDegree{" +
            "id=" + getId() +
            ", institutionName='" + getInstitutionName() + "'" +
            ", discipline='" + getDiscipline() + "'" +
            ", startYear=" + getStartYear() +
            ", endYear=" + getEndYear() +
            ", obtainedDegree='" + getObtainedDegree() + "'" +
            "}";
    }
}
