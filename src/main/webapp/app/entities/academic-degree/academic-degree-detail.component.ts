import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAcademicDegree } from 'app/shared/model/academic-degree.model';

@Component({
  selector: 'jhi-academic-degree-detail',
  templateUrl: './academic-degree-detail.component.html',
})
export class AcademicDegreeDetailComponent implements OnInit {
  academicDegree: IAcademicDegree | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ academicDegree }) => (this.academicDegree = academicDegree));
  }

  previousState(): void {
    window.history.back();
  }
}
