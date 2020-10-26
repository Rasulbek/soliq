import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAcademicDegree, AcademicDegree } from 'app/shared/model/academic-degree.model';
import { AcademicDegreeService } from './academic-degree.service';
import { AcademicDegreeComponent } from './academic-degree.component';
import { AcademicDegreeDetailComponent } from './academic-degree-detail.component';
import { AcademicDegreeUpdateComponent } from './academic-degree-update.component';

@Injectable({ providedIn: 'root' })
export class AcademicDegreeResolve implements Resolve<IAcademicDegree> {
  constructor(private service: AcademicDegreeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAcademicDegree> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((academicDegree: HttpResponse<AcademicDegree>) => {
          if (academicDegree.body) {
            return of(academicDegree.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AcademicDegree());
  }
}

export const academicDegreeRoute: Routes = [
  {
    path: '',
    component: AcademicDegreeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'soliqApp.academicDegree.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AcademicDegreeDetailComponent,
    resolve: {
      academicDegree: AcademicDegreeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'soliqApp.academicDegree.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AcademicDegreeUpdateComponent,
    resolve: {
      academicDegree: AcademicDegreeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'soliqApp.academicDegree.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AcademicDegreeUpdateComponent,
    resolve: {
      academicDegree: AcademicDegreeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'soliqApp.academicDegree.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
