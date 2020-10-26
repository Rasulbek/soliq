package uz.soliq.anketa.service.mapper;


import uz.soliq.anketa.domain.*;
import uz.soliq.anketa.service.dto.JobHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobHistory} and its DTO {@link JobHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface JobHistoryMapper extends EntityMapper<JobHistoryDTO, JobHistory> {

    @Mapping(source = "employee.id", target = "employeeId")
    JobHistoryDTO toDto(JobHistory jobHistory);

    @Mapping(source = "employeeId", target = "employee")
    JobHistory toEntity(JobHistoryDTO jobHistoryDTO);

    default JobHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobHistory jobHistory = new JobHistory();
        jobHistory.setId(id);
        return jobHistory;
    }
}
