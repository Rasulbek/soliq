import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { SoliqTestModule } from '../../../test.module';
import { AcademicDegreeComponent } from 'app/entities/academic-degree/academic-degree.component';
import { AcademicDegreeService } from 'app/entities/academic-degree/academic-degree.service';
import { AcademicDegree } from 'app/shared/model/academic-degree.model';

describe('Component Tests', () => {
  describe('AcademicDegree Management Component', () => {
    let comp: AcademicDegreeComponent;
    let fixture: ComponentFixture<AcademicDegreeComponent>;
    let service: AcademicDegreeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoliqTestModule],
        declarations: [AcademicDegreeComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(AcademicDegreeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AcademicDegreeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AcademicDegreeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AcademicDegree(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.academicDegrees && comp.academicDegrees[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AcademicDegree(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.academicDegrees && comp.academicDegrees[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
