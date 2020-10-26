import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoliqSharedModule } from 'app/shared/shared.module';
import { AcademicDegreeComponent } from './academic-degree.component';
import { AcademicDegreeDetailComponent } from './academic-degree-detail.component';
import { AcademicDegreeUpdateComponent } from './academic-degree-update.component';
import { AcademicDegreeDeleteDialogComponent } from './academic-degree-delete-dialog.component';
import { academicDegreeRoute } from './academic-degree.route';

@NgModule({
  imports: [SoliqSharedModule, RouterModule.forChild(academicDegreeRoute)],
  declarations: [
    AcademicDegreeComponent,
    AcademicDegreeDetailComponent,
    AcademicDegreeUpdateComponent,
    AcademicDegreeDeleteDialogComponent,
  ],
  entryComponents: [AcademicDegreeDeleteDialogComponent],
})
export class SoliqAcademicDegreeModule {}
