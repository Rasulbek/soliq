package uz.soliq.anketa.service.mapper;


import uz.soliq.anketa.domain.*;
import uz.soliq.anketa.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {


    @Mapping(target = "employers", ignore = true)
    @Mapping(target = "removeEmployer", ignore = true)
    @Mapping(target = "universities", ignore = true)
    @Mapping(target = "removeUniversity", ignore = true)
    Employee toEntity(EmployeeDTO employeeDTO);

    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
