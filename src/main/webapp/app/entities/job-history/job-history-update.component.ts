import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJobHistory, JobHistory } from 'app/shared/model/job-history.model';
import { JobHistoryService } from './job-history.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Component({
  selector: 'jhi-job-history-update',
  templateUrl: './job-history-update.component.html',
})
export class JobHistoryUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];

  editForm = this.fb.group({
    id: [],
    employer: [null, [Validators.required, Validators.maxLength(250)]],
    jobTitle: [null, [Validators.required, Validators.maxLength(250)]],
    startYear: [null, [Validators.required, Validators.min(1950), Validators.max(2020)]],
    endYear: [null, [Validators.min(1950), Validators.max(2050)]],
    employeeId: [],
  });

  constructor(
    protected jobHistoryService: JobHistoryService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobHistory }) => {
      this.updateForm(jobHistory);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(jobHistory: IJobHistory): void {
    this.editForm.patchValue({
      id: jobHistory.id,
      employer: jobHistory.employer,
      jobTitle: jobHistory.jobTitle,
      startYear: jobHistory.startYear,
      endYear: jobHistory.endYear,
      employeeId: jobHistory.employeeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobHistory = this.createFromForm();
    if (jobHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.jobHistoryService.update(jobHistory));
    } else {
      this.subscribeToSaveResponse(this.jobHistoryService.create(jobHistory));
    }
  }

  private createFromForm(): IJobHistory {
    return {
      ...new JobHistory(),
      id: this.editForm.get(['id'])!.value,
      employer: this.editForm.get(['employer'])!.value,
      jobTitle: this.editForm.get(['jobTitle'])!.value,
      startYear: this.editForm.get(['startYear'])!.value,
      endYear: this.editForm.get(['endYear'])!.value,
      employeeId: this.editForm.get(['employeeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobHistory>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IEmployee): any {
    return item.id;
  }
}
