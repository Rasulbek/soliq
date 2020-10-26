import { Degree } from 'app/shared/model/enumerations/degree.model';

export interface IAcademicDegree {
  id?: number;
  institutionName?: string;
  discipline?: string;
  startYear?: number;
  endYear?: number;
  obtainedDegree?: Degree;
  employeeId?: number;
}

export class AcademicDegree implements IAcademicDegree {
  constructor(
    public id?: number,
    public institutionName?: string,
    public discipline?: string,
    public startYear?: number,
    public endYear?: number,
    public obtainedDegree?: Degree,
    public employeeId?: number
  ) {}
}
