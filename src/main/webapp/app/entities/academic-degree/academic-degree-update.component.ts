import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAcademicDegree, AcademicDegree } from 'app/shared/model/academic-degree.model';
import { AcademicDegreeService } from './academic-degree.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Component({
  selector: 'jhi-academic-degree-update',
  templateUrl: './academic-degree-update.component.html',
})
export class AcademicDegreeUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];

  editForm = this.fb.group({
    id: [],
    institutionName: [null, [Validators.required, Validators.maxLength(250)]],
    discipline: [null, [Validators.required, Validators.maxLength(250)]],
    startYear: [null, [Validators.required, Validators.min(1950), Validators.max(2050)]],
    endYear: [null, [Validators.min(1950), Validators.max(2050)]],
    obtainedDegree: [null, [Validators.required]],
    employeeId: [],
  });

  constructor(
    protected academicDegreeService: AcademicDegreeService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ academicDegree }) => {
      this.updateForm(academicDegree);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(academicDegree: IAcademicDegree): void {
    this.editForm.patchValue({
      id: academicDegree.id,
      institutionName: academicDegree.institutionName,
      discipline: academicDegree.discipline,
      startYear: academicDegree.startYear,
      endYear: academicDegree.endYear,
      obtainedDegree: academicDegree.obtainedDegree,
      employeeId: academicDegree.employeeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const academicDegree = this.createFromForm();
    if (academicDegree.id !== undefined) {
      this.subscribeToSaveResponse(this.academicDegreeService.update(academicDegree));
    } else {
      this.subscribeToSaveResponse(this.academicDegreeService.create(academicDegree));
    }
  }

  private createFromForm(): IAcademicDegree {
    return {
      ...new AcademicDegree(),
      id: this.editForm.get(['id'])!.value,
      institutionName: this.editForm.get(['institutionName'])!.value,
      discipline: this.editForm.get(['discipline'])!.value,
      startYear: this.editForm.get(['startYear'])!.value,
      endYear: this.editForm.get(['endYear'])!.value,
      obtainedDegree: this.editForm.get(['obtainedDegree'])!.value,
      employeeId: this.editForm.get(['employeeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAcademicDegree>>): void {
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
