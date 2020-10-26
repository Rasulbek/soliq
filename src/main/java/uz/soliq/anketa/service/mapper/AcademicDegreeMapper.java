package uz.soliq.anketa.service.mapper;


import uz.soliq.anketa.domain.*;
import uz.soliq.anketa.service.dto.AcademicDegreeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AcademicDegree} and its DTO {@link AcademicDegreeDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface AcademicDegreeMapper extends EntityMapper<AcademicDegreeDTO, AcademicDegree> {

    @Mapping(source = "employee.id", target = "employeeId")
    AcademicDegreeDTO toDto(AcademicDegree academicDegree);

    @Mapping(source = "employeeId", target = "employee")
    AcademicDegree toEntity(AcademicDegreeDTO academicDegreeDTO);

    default AcademicDegree fromId(Long id) {
        if (id == null) {
            return null;
        }
        AcademicDegree academicDegree = new AcademicDegree();
        academicDegree.setId(id);
        return academicDegree;
    }
}
