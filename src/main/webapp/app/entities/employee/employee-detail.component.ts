import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEmployee } from 'app/shared/model/employee.model';
import { IAcademicDegree } from '../../shared/model/academic-degree.model';
import { AcademicDegreeDeleteDialogComponent } from '../academic-degree/academic-degree-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IJobHistory } from '../../shared/model/job-history.model';
import { JobHistoryDeleteDialogComponent } from '../job-history/job-history-delete-dialog.component';

@Component({
  selector: 'jhi-employee-detail',
  templateUrl: './employee-detail.component.html',
})
export class EmployeeDetailComponent implements OnInit {
  employee: IEmployee | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => (this.employee = employee));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }

  delete(academicDegree: IAcademicDegree): void {
    const modalRef = this.modalService.open(AcademicDegreeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.academicDegree = academicDegree;
  }

  deleteJob(jobHistory: IJobHistory): void {
    const modalRef = this.modalService.open(JobHistoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jobHistory = jobHistory;
  }
}
