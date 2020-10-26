import { Moment } from 'moment';
import { IJobHistory } from 'app/shared/model/job-history.model';
import { IAcademicDegree } from 'app/shared/model/academic-degree.model';

export interface IEmployee {
  id?: number;
  fullName?: string;
  email?: string;
  phoneNumber?: string;
  birthday?: Moment;
  nation?: string;
  photoContentType?: string;
  photo?: any;
  employers?: IJobHistory[];
  universities?: IAcademicDegree[];
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public fullName?: string,
    public email?: string,
    public phoneNumber?: string,
    public birthday?: Moment,
    public nation?: string,
    public photoContentType?: string,
    public photo?: any,
    public employers?: IJobHistory[],
    public universities?: IAcademicDegree[]
  ) {}
}
