export interface IJobHistory {
  id?: number;
  employer?: string;
  jobTitle?: string;
  startYear?: number;
  endYear?: number;
  employeeId?: number;
  employeeName?: string;
}

export class JobHistory implements IJobHistory {
  constructor(
    public id?: number,
    public employer?: string,
    public jobTitle?: string,
    public startYear?: number,
    public endYear?: number,
    public employeeId?: number,
    public employeeName?: string
  ) {}
}
