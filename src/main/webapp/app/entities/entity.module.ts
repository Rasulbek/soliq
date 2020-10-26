import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.SoliqEmployeeModule),
      },
      {
        path: 'job-history',
        loadChildren: () => import('./job-history/job-history.module').then(m => m.SoliqJobHistoryModule),
      },
      {
        path: 'academic-degree',
        loadChildren: () => import('./academic-degree/academic-degree.module').then(m => m.SoliqAcademicDegreeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SoliqEntityModule {}
