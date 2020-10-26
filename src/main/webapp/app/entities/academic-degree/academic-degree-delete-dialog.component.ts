import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAcademicDegree } from 'app/shared/model/academic-degree.model';
import { AcademicDegreeService } from './academic-degree.service';

@Component({
  templateUrl: './academic-degree-delete-dialog.component.html',
})
export class AcademicDegreeDeleteDialogComponent {
  academicDegree?: IAcademicDegree;

  constructor(
    protected academicDegreeService: AcademicDegreeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.academicDegreeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('academicDegreeListModification');
      this.activeModal.close();
    });
  }
}
