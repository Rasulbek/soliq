import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoliqTestModule } from '../../../test.module';
import { AcademicDegreeDetailComponent } from 'app/entities/academic-degree/academic-degree-detail.component';
import { AcademicDegree } from 'app/shared/model/academic-degree.model';

describe('Component Tests', () => {
  describe('AcademicDegree Management Detail Component', () => {
    let comp: AcademicDegreeDetailComponent;
    let fixture: ComponentFixture<AcademicDegreeDetailComponent>;
    const route = ({ data: of({ academicDegree: new AcademicDegree(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoliqTestModule],
        declarations: [AcademicDegreeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AcademicDegreeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AcademicDegreeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load academicDegree on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.academicDegree).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
